package edu.nju.test.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.test.bean.*;
import edu.nju.test.dao.*;
import edu.nju.test.domain.*;
import edu.nju.test.exception.PaperFullException;
import edu.nju.test.exception.TimeExceededException;
import edu.nju.test.exception.TimeInvalidException;
import edu.nju.test.exception.WrongTestCodeException;
import edu.nju.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    @Autowired
    private final TestDAO testDAO;
    @Autowired
    private final ParticipateDAO participateDAO;
    @Autowired
    private final QuestionDAO questionDAO;
    @Autowired
    private final PaperDAO paperDAO;
    @Autowired
    private final TestLibDAO testLibDAO;
    @Autowired
    private final AccountDAO accountDAO;


    public TestServiceImpl(TestDAO testDAO, ParticipateDAO participateDAO, QuestionDAO questionDAO, PaperDAO paperDAO, TestLibDAO testLibDAO, AccountDAO accountDAO) {
        this.testDAO = testDAO;
        this.participateDAO = participateDAO;
        this.questionDAO = questionDAO;
        this.paperDAO = paperDAO;
        this.testLibDAO = testLibDAO;
        this.accountDAO = accountDAO;
    }

    private void setOptionsAndType(QuestionBean qb, Set<Option> opSet){
        List<OptionBean> options = new ArrayList<>();
        int rightNum = 0;
        for(Option op:opSet){
            OptionBean optionBean = new OptionBean(op.getId(), op.getName());
            options.add(optionBean);
            if (op.isRight()){
                rightNum++;
            }
        }
        if (rightNum > 1){
            qb.setType(1); // 多选
        } else {
            qb.setType(0); // 单选
        }
        Collections.shuffle(options); // 选项随机排序
        qb.setOptions(options);
    }

    /**
     * 生成考试卷的题目，默认总分100
     * @param questions 题库包含的所有题目
     * @param quesNum 题目总数
     * @return 生成的题目
     */
    public List<QuestionBean> generateQuestions(List<QuestionBean> questions, int quesNum){
        final BigDecimal totalScore = new BigDecimal(100); // 总分是100
        TreeMap<BigDecimal,List<QuestionBean>> quesMap = new TreeMap<>();

        // 把不同分值的题目分开做成map
        for(QuestionBean question : questions){
            BigDecimal sc = question.getScore();
            if(quesMap.keySet().contains(sc)){
                quesMap.get(sc).add(question);
            }else{
                List<QuestionBean> tempList = new ArrayList<>();
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

        List<QuestionBean> ansList = new ArrayList<>();
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
    public TestBean getTest(String testCode) throws WrongTestCodeException, PaperFullException, TimeInvalidException {
        Participate p = participateDAO.findByPassword(testCode);
        if(p==null)throw new WrongTestCodeException("wrong code");
        Paper paper = paperDAO.findByUserIdAndTestId(p.getUserId(), p.getTestId());
        Test t  = testDAO.findById(p.getTestId());
        Calendar now = Calendar.getInstance();
        if (!(now.after(t.getStartTime()) && now.before(t.getEndTime()))){ // 不在区间内
            throw new TimeInvalidException();
        }
        if (paper == null){ // 如果事先没有生成试卷，则现在生成一个
            List<QuestionBean> ques = new ArrayList<>();
            Set<TestLib> s = t.getLibSet();
            for(TestLib lib:s){
                Question q = questionDAO.findById(lib.getQuesId());
                QuestionBean qb = new QuestionBean();
                qb.setDescription(q.getContent());
                qb.setId(q.getId());
                qb.setScore(lib.getScore());
                setOptionsAndType(qb, q.getOptions());
                ques.add(qb);
            }
            Collections.shuffle(ques); // 题目随机排序
            List<QuestionBean> questions = generateQuestions(ques, t.getQuesNum());
            for (int i = 0;i < questions.size();i++){
                questions.get(i).setOrder(i);
            }

            TestBean testBean = new TestBean(t.getId(),t.getSubject(), t.getQuesNum(), questions.get(0),
                    t.getStartTime(),t.getEndTime());
            savePaper(questions, t.getId(), p.getUserId());
            return testBean;
        } else { // 如果已经有生成的试卷，则将已生成的试卷返回
            Set<TestQue> testQues = paper.getTestQues();
            List<TestQue> testQueList = new ArrayList<>(testQues);
            Collections.sort(testQueList);
            TestQue testQue = testQueList.get(0);
            Question q = testQue.getQuestion();
            QuestionBean qb = new QuestionBean();

            qb.setDescription(q.getContent());
            qb.setId(q.getId());
            qb.setOrder(testQue.getOrder());

            TestLib lib = testLibDAO.findByTestIdAndAndQuesId(testQue.getPaperId().substring(0, 10), testQue.getQuesId());
            qb.setScore(lib.getScore());
            setOptionsAndType(qb, q.getOptions());
            return new TestBean(t.getId(),t.getSubject(), t.getQuesNum(), qb, t.getStartTime(),t.getEndTime());
        }
    }

    /**
     * 把试题set转换为map
     * @param testQueSet 试题Set
     * @return map
     */
    private Map<String, TestQue> setToMap(Set<TestQue> testQueSet){
        Map<String, TestQue> map = new HashMap<>();
        for (TestQue testQue:testQueSet){
            map.put(testQue.getQuesId(), testQue);
        }
        return map;
    }

    /**
     * 把选项列表转换为字符串
     * @param options 选项列表
     * @return 转换成的字符串，数字选项以英文逗号隔开
     */
    private String transferOptionsToString(List<Integer> options){
        if (options == null || options.size() == 0 || options.get(0) == -1){
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Integer op:options) {
            result.append(op);
            result.append(',');
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /**
     * 向邮箱发送密码
     * @param email 邮箱
     * @param subject 科目名称
     * @param score 考试得分
     * @return 发送结果
     */
    private int sendResult(String email, String subject, BigDecimal score){
        try {
            // 发送请求
            URL url = new URL("http://47.100.125.54:8099/mailbox/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            con.setUseCaches(false);
            // 输出参数
            PrintWriter writer = new PrintWriter(con.getOutputStream());
            StringBuffer params = new StringBuffer();
            params.append("email=");
            params.append(email);
            params.append("&content=");
            params.append("您的考试").append(subject).append("的得分是").append(score).append("分");
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

    @Override
    @Transactional
    public TestResultBean getTestResult(UserTestBean userTestBean) throws TimeExceededException {
        List<AnswerBean> userOps = userTestBean.getUserOptions();
        BigDecimal score = new BigDecimal(0);
        Paper p = paperDAO.findByUserIdAndTestId(userTestBean.getUserId(), userTestBean.getTestId());
        Map<String, TestQue> testQueMap = setToMap(p.getTestQues());
        for(AnswerBean qb:userOps) {
            Question q = questionDAO.findById(qb.getId());
            TestQue t = testQueMap.get(qb.getId());
            t.setOption(transferOptionsToString(qb.getOptions())); // 设置选项字符串
            if (t.getOption().equals("")){
                continue;
            }
            boolean isRight = true;
            List<Integer> opis = new ArrayList<>(qb.getOptions());
            for (Option option:q.getOptions()){ // 比对选项，看是否正确
                boolean right = option.isRight();
                if (right){ // 如果是正确选项
                    if (!opis.contains(option.getId())){ // 但是没有选
                        isRight = false; // 就知道没得分，可以退出循环
                        break;
                    }
                }
            }
            if (isRight){
                TestLib testLib = testLibDAO.findByTestIdAndAndQuesId(userTestBean.getTestId(), qb.getId());
                BigDecimal s = testLib.getScore();
                t.setScore(s);
                score = score.add(s);
            }
        }
        p.setTestQues(new HashSet<>(testQueMap.values()));
        Test test = testDAO.findById(userTestBean.getTestId());
        String subject = test.getSubject();
        Account account = accountDAO.findById(userTestBean.getUserId());
        int r = sendResult(account.getEmail(), subject, score);
        if (r == 0){
            paperDAO.save(p);
            return new TestResultBean(userTestBean.getUserId(),userTestBean.getTestId(),score);
        } else {
            return null;
        }

    }

    @Override
    public boolean saveTestResult(TestResultBean testResult) {
        participateDAO.updateScore(testResult.getScore(),testResult.getUserId(),testResult.getTestId());
        return true;
    }

    @Override
    public QuestionBean getTestQue(String testCode, int order) throws WrongTestCodeException {
        Participate p = participateDAO.findByPassword(testCode);
        if(p==null)throw new WrongTestCodeException("wrong code");
        Paper paper = paperDAO.findByUserIdAndTestId(p.getUserId(), p.getTestId());
        List<TestQue> testQueList = new ArrayList<>(paper.getTestQues());
        Collections.sort(testQueList);
        TestQue que = testQueList.get(order);
        QuestionBean questionBean = new QuestionBean();
        Question q = que.getQuestion();
        questionBean.setId(que.getQuesId());
        questionBean.setDescription(q.getContent());
        TestLib testLib = testLibDAO.findByTestIdAndAndQuesId(p.getTestId(), que.getQuesId());
        questionBean.setScore(testLib.getScore());
        questionBean.setUserOption("");
        questionBean.setOrder(que.getOrder());
        setOptionsAndType(questionBean, q.getOptions());
        return questionBean;
    }

    private String generatePaperId(String testId) throws PaperFullException {
        List<Paper> papers = paperDAO.findByTestId(testId);
        if (papers.size() == 0){ // 无库，新建为000
            return testId + "000";
        }
        int lastNum = -1;
        for (Paper p:papers) { // 对库ID遍历，有空则插入，否则放到最后，超出100个抛异常
            String id = p.getId();
            String idNum = id.substring(10);
            int nowNum = Integer.parseInt(idNum);
            if (nowNum != lastNum + 1){
                return testId + String.format("%03d", lastNum + 1);
            } else {
                ++lastNum;
            }
        }
        // 跳出表示没有插空
        if (lastNum >= 999){
            throw new PaperFullException();
        } else {
            return testId + String.format("%03d", lastNum + 1);
        }
    }

    /**
     * 保存生成的试卷
     * @param questionList 问题列表
     * @param testId 考试ID
     * @param userId 考试用户ID
     */
    private void savePaper(List<QuestionBean> questionList, String testId, String userId) throws PaperFullException {
        Paper paper = new Paper();
        paper.setId(generatePaperId(testId));
        paper.setTestId(testId);
        paper.setUserId(userId);
        Set<TestQue> testQueSet = new HashSet<>();
        for (int i = 0; i < questionList.size(); i++){
            TestQue t = new TestQue();
            t.setOption("");
            t.setOrder(questionList.get(i).getOrder());
            t.setPaperId(paper.getId());
            t.setScore(BigDecimal.ZERO);
            t.setQuesId(questionList.get(i).getId());
            testQueSet.add(t);
        }
        paper.setTestQues(testQueSet);
        paperDAO.save(paper);
    }
}
