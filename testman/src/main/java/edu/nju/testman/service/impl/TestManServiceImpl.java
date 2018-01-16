package edu.nju.testman.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.testman.bean.*;
import edu.nju.testman.dao.AccountDAO;
import edu.nju.testman.dao.TestManDAO;
import edu.nju.testman.domain.Account;
import edu.nju.testman.domain.Participate;
import edu.nju.testman.domain.Test;
import edu.nju.testman.domain.TestLib;
import edu.nju.testman.exception.EmailInvalidException;
import edu.nju.testman.exception.StudentFullException;
import edu.nju.testman.exception.TestFullException;
import edu.nju.testman.service.TestManService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestManServiceImpl implements TestManService {

    private final TestManDAO dao;
    private final AccountDAO accountDAO;

    @Autowired
    public TestManServiceImpl(TestManDAO dao, AccountDAO accountDAO) {
        this.dao = dao;
        this.accountDAO = accountDAO;
    }

    private String generateId(Calendar startTime) throws TestFullException{
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DATE), 0, 0, 0);
        end.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DATE), 23, 59, 59);
        List<Test> tests = dao.findByStartTimeBetween(start, end);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        if (tests.size() == 0){ // 无考试，新建为00
            return format.format(startTime.getTime()) + "00";
        }
        int lastNum = -1;
        for (Test test:tests) { // 对考试ID遍历，有空则插入，否则放到最后，超出100个抛异常
            String id = test.getId();
            String idNum = id.substring(8);
            int nowNum = Integer.parseInt(idNum);
            if (nowNum != lastNum + 1){
                return format.format(startTime.getTime()) + String.format("%02d", lastNum + 1);
            } else {
                ++lastNum;
            }
        }
        // 跳出表示没有插空
        if (lastNum >= 99){
            throw new TestFullException();
        } else {
            return format.format(startTime.getTime()) + String.format("%02d", lastNum + 1);
        }
    }

    /**
     * 生成考试密码
     * @param testId 考试ID
     * @param userId 用户ID
     * @return 考试密码
     */
    private String generatePassword(String testId, String userId){
        char pw[] = new char[20];
        int last = 0;
        for (int i = 0;i < 10;i++){
            int v = testId.charAt(i) - '0';
            pw[i * 2] = (char) ((v * v % 26) + 'a');
            pw[i * 2 + 1] = (char) ((v + last) % 26 + 'a');
            last = v;
        }
        last = 0;
        for (int i = 0;i < 5;i++){
            int v = userId.charAt(i) - '0';
            pw[i * 4] = (char)((pw[i * 4] - 'a' + v * v) % 26 + 'a');
            pw[i * 4 + 1] = (char)((pw[i * 4 + 1] - 'a' + v + last) % 26 + 'a');
            pw[i * 4 + 2] = (char)((pw[i * 4 + 2] - 'a' + v * v + last) % 26 + 'a');
            pw[i * 4 + 3] = (char)((pw[i * 4 + 3] + v + last) * (pw[i * 4 + 3] + v + last) % 26 + 'a');
        }
        return new String(pw);
    }

    /**
     * 设置考试信息
     * @param testBean 考试Bean
     * @param isNew 是否新建
     * @return 考试实体
     */
    private Test setTestInfo(TestInBean testBean, boolean isNew) throws TestFullException, EmailInvalidException, IOException, StudentFullException {
        Test test = new Test();
        // 考试基本信息
        if (isNew){
            test.setId(generateId(testBean.getStartTime()));
        } else {
            test.setId(testBean.getTestId());
        }
        test.setCreator(testBean.getUserId());
        test.setSubject(testBean.getTestName());
        test.setEndTime(testBean.getEndTime());
        test.setStartTime(testBean.getStartTime());
        test.setLibId(testBean.getLibId());
        test.setQuesNum(testBean.getQuesNum());

        // 考试题目信息
        Set<TestLib> testLibSet = new HashSet<>();
        for (QIDScoreBean q: testBean.getQuestions()) {
            TestLib testLib = new TestLib();
            testLib.setTestId(test.getId());
            testLib.setQuesId(q.getId());
            testLib.setScore(q.getScore());
            testLibSet.add(testLib);
        }
        test.setLibSet(testLibSet);

        // 参考基本信息
        Set<Participate> participateSet = new HashSet<>();
        List<StudentBean> studentList = testBean.getStudents();
        for (StudentBean student : studentList) {
            Account account = accountDAO.findByEmail(student.getEmail());
            if (account == null){
                throw new EmailInvalidException();
            }
            Participate participate = new Participate();
            participate.setUserId(account.getId());
            participate.setTestId(test.getId());
            participate.setEmail(student.getEmail());
            participate.setClassName(student.getClassName());
            participate.setGrade(student.getGrade());
            participate.setName(student.getName());
            participate.setStudentId(student.getStudentId());
            participate.setPassword(generatePassword(participate.getTestId(), participate.getUserId()));
            participateSet.add(participate);
        }
        test.setParticipateSet(participateSet);

        return test;
    }

    /**
     * 向邮箱发送密码
     * @param email 邮箱
     * @param password 密码
     * @param subject 科目名称
     * @return 发送结果
     */
    private int sendPassword(String email, String password, String subject){
        try {
            // 发送请求
            URL url = new URL("http://47.100.125.54:8099/mailbox/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setUseCaches(false);
            // 输出参数
            PrintWriter writer = new PrintWriter(con.getOutputStream());
            StringBuffer params = new StringBuffer();
            params.append("email=");
            params.append(email);
            params.append("&content=");
            params.append("您的考试").append(subject).append("的密码是").append(password);
            writer.print(params);
            writer.flush();
            writer.close();
            // 读取数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
                response.append("\n");
            }
            reader.close();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());
            return root.asInt();
        } catch (IOException e) {
            return 4;
        }
    }

    /**
     * 生成考试卷的题目，默认总分100
     * @param questions 题库包含的所有题目
     * @param quesNum 题目总数
     * @return 生成的题目
     */
    public List<TestLib> generateQuestions(List<TestLib> questions, int quesNum){
        final BigDecimal totalScore = new BigDecimal(100); // 总分是100
        TreeMap<BigDecimal,List<TestLib>> quesMap = new TreeMap<>();

        // 把不同分值的题目分开做成map
        for(TestLib question : questions){
            BigDecimal sc = question.getScore();
            if(quesMap.keySet().contains(sc)){
                quesMap.get(sc).add(question);
            }else{
                List<TestLib> tempList = new ArrayList<>();
                tempList.add(question);
                quesMap.put(sc,tempList);
            }
        }

        BigDecimal[] weight = new BigDecimal[quesMap.keySet().size()];
        int[] nums = new int[quesMap.keySet().size()];

        int count=0;
        for(BigDecimal sc:quesMap.keySet()){
            weight[count] = sc;
            nums[count] = quesMap.get(sc).size();
            count++;
        }

        Set<List<Integer>>[][][] tempAns = new Set[count][quesNum+1][totalScore.intValue()+1];

        for(int j = 0;j <= Math.min(quesNum,nums[0]);j++){
            tempAns[0][j][weight[0].intValue()*j]= new HashSet<>();
            List<Integer> tempList = new ArrayList<>();
            tempList.add(j);
            tempAns[0][j][weight[0].intValue()*j].add(tempList);
        }
        for(int i = 1;i < count;i++){
            for(int j = 0;j <= quesNum;j++){
                for(int k = 0;k <= totalScore.intValue();k++){
                    for(int currentNum = 0;currentNum <= nums[i];currentNum++){
                        if(currentNum > j)break;
                        if(weight[i].intValue()*currentNum > k)break;
                        //f[i][j][k]=f[i-1][j][k]+f[i-1][j-1][k-weight[i]]+....
                        if(tempAns[i-1][j-currentNum][k-weight[i].intValue()*currentNum] == null){
                            continue;
                        }
                        if(tempAns[i][j][k] == null) tempAns[i][j][k] = new HashSet<>();
                        for(List<Integer> list:tempAns[i-1][j-currentNum][k-weight[i].intValue()*currentNum]){
                            List<Integer> newList = new ArrayList<>(list);
                            newList.add(currentNum);
                            tempAns[i][j][k].add(newList);
                        }
                    }
                }
            }
        }

        List<TestLib> ansList = new ArrayList<>();
        if(tempAns[count-1][quesNum][totalScore.intValue()] == null){
            return ansList;
        }
        List<Integer> l = tempAns[count - 1][quesNum][totalScore.intValue()].iterator().next();
        for(int i=0;i<l.size();i++){
            ansList.addAll(quesMap.get(weight[i]).subList(0,l.get(i)));
        }

        return ansList;
    }

    @Override
    @Transactional
    public int generateTest(TestInBean testBean) {
        try {
            Test test = setTestInfo(testBean, true);
            String subject = test.getSubject();
            List<TestLib> ansList = generateQuestions(new ArrayList<>(test.getLibSet()), test.getQuesNum());
            if (ansList.size() == 0){
                return 0x203;
            }
            dao.save(test);
            for (Participate participate:test.getParticipateSet()){
                int r = sendPassword(participate.getEmail(), participate.getPassword(), subject);
                if (r != 0){
                    return 4;
                }
            }
            return 0;
        } catch (TestFullException e) {
            return 0x200;
        } catch (EmailInvalidException e) {
            return 0x201;
        } catch (IOException e) {
            return 3;
        } catch (StudentFullException e) {
            return 0x202;
        }
    }

    @Override
    public int changeTest(TestInBean testBean) {
        try {
            Test test = setTestInfo(testBean, false);
            String subject = test.getSubject();
            dao.save(test);
            for (Participate participate:test.getParticipateSet()){
                int r = sendPassword(participate.getEmail(), participate.getPassword(), subject);
                if (r != 0){
                    return 4;
                }
            }
            return 0;
        } catch (TestFullException e) {
            return 0x200;
        } catch (EmailInvalidException e) {
            return 0x201;
        } catch (IOException e) {
            return 3;
        } catch (StudentFullException e) {
            return 0x202;
        }
    }

    @Override
    public List<TestNameBean> getTestList(String userId) {
        List<Test> tests = dao.findByCreator(userId);
        List<TestNameBean> testList = new ArrayList<>();
        for (Test test:tests) {
            TestNameBean testNameBean = new TestNameBean();
            testNameBean.setId(test.getId());
            testNameBean.setName(test.getSubject());
            testNameBean.setStartTime(test.getStartTime());
            testNameBean.setEndTime(test.getEndTime());
            testList.add(testNameBean);
        }
        return testList;
    }

    @Override
    public TestBean getTest(String id) {
        Test test = dao.findById(id);
        TestBean testBean = new TestBean();
        // 设置考试基本信息
        testBean.setUserId(test.getCreator());
        testBean.setTestId(test.getId());
        testBean.setStartTime(test.getStartTime());
        testBean.setEndTime(test.getEndTime());
        testBean.setTestName(test.getSubject());
        testBean.setLibId(test.getLibId());
        // 设置考试题目信息
        List<QIDScoreBean> scoreBeans = new ArrayList<>();
        Set<TestLib> testLibs = test.getLibSet();
        for (TestLib testLib:testLibs) {
            QIDScoreBean scoreBean = new QIDScoreBean();
            scoreBean.setId(testLib.getQuesId());
            scoreBean.setScore(testLib.getScore());
            scoreBean.setContent(testLib.getQuestion().getContent());
            scoreBeans.add(scoreBean);
        }
        testBean.setQuestions(scoreBeans);
        // 设置参考学生邮箱信息
        List<StudentReBean> students = new ArrayList<>();
        Set<Participate> participates = test.getParticipateSet();
        for (Participate participate:participates) {
            Account account = accountDAO.findById(participate.getUserId());
            StudentReBean studentBean = new StudentReBean();
            studentBean.setId(participate.getUserId());
            studentBean.setEmail(account.getEmail());
            studentBean.setName(participate.getName());
            studentBean.setClassName(participate.getClassName());
            studentBean.setGrade(participate.getGrade());
            studentBean.setStudentId(participate.getStudentId());
            students.add(studentBean);
        }
        testBean.setStudents(students);
        return testBean;
    }

    @Override
    public int deleteTest(String id) {
        dao.delete(id);
        return 0;
    }
}
