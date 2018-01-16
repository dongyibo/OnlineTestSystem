package edu.nju.paper.service.impl;

import edu.nju.paper.bean.*;
import edu.nju.paper.dao.PaperDAO;
import edu.nju.paper.dao.ParticipateDAO;
import edu.nju.paper.dao.QuestionDAO;
import edu.nju.paper.dao.TestDAO;
import edu.nju.paper.domain.*;
import edu.nju.paper.exception.PaperFullException;
import edu.nju.paper.exception.TestEndException;
import edu.nju.paper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dongyibo on 2017/11/27.
 */
@Service
public class PaperServiceImpl implements PaperService {

    private final PaperDAO paperDAO;
    private final TestDAO testDAO;
    private final QuestionDAO questionDAO;
    private final ParticipateDAO participateDAO;

    @Autowired
    public PaperServiceImpl(PaperDAO paperDAO, TestDAO testDAO, QuestionDAO questionDAO, ParticipateDAO participateDAO) {
        this.paperDAO = paperDAO;
        this.testDAO = testDAO;
        this.questionDAO = questionDAO;
        this.participateDAO = participateDAO;
    }


    private void setOptionsAndType(QuestionBean qb, Set<Option> opSet){
        List<OptionBean> options = new ArrayList<>();
        int rightNum = 0;
        for(Option op:opSet){
            OptionBean optionBean = new OptionBean(op.getName(), op.getId());
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
     * 保存生成的试卷
     * @param singlePaperBean 考试信息
     * @param userId 考试用户ID
     */
    private void savePaper(SinglePaperBean singlePaperBean, String userId, String testId) {
        Paper paper = new Paper();
        paper.setId(singlePaperBean.getPaperId());
        paper.setTestId(testId);
        paper.setUserId(userId);
        Set<TestQue> testQueSet = new HashSet<>();
        List<QuestionBean> questions = singlePaperBean.getQuestions();
        for (int i = 0; i < questions.size(); i++){
            TestQue t = new TestQue();
            t.setOption("");
            t.setOrder(questions.get(i).getOrder());
            t.setPaperId(paper.getId());
            t.setScore(BigDecimal.ZERO);
            t.setQuesId(questions.get(i).getId());
            testQueSet.add(t);
        }
        paper.setTestQues(testQueSet);
        paperDAO.save(paper);
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
    public PaperBean generatePapers(String testId, String teacher, List<String> students) throws PaperFullException, TestEndException {
        int stuNum = (students != null && students.size() > 0) ? students.size() : 0;
        ArrayList<SinglePaperBean> singlePaperBeans = new ArrayList<>();
        Test t  = testDAO.findById(testId);
        // 为每个考生生成试卷
        for (int i = 0; i < stuNum; i++) {
            String studentId = students.get(i);
            Participate p = participateDAO.findByUserIdAndTestId(studentId, testId);
            if (p.getScore() != null){
                throw new TestEndException();
            }
            List<QuestionBean> ques = new ArrayList<>();
            Set<TestLib> s = t.getLibSet();
            for(TestLib lib:s){
                Question q = questionDAO.findById(lib.getQuesId());
                QuestionBean qb = new QuestionBean();
                qb.setId(q.getId());
                qb.setContent(q.getContent());
                qb.setScore(lib.getScore());
                setOptionsAndType(qb, q.getOptions());
                ques.add(qb);
            }
            Collections.shuffle(ques); // 题目随机排序
            ques = generateQuestions(ques, t.getQuesNum());
            for (int j = 0;j < ques.size();j++){
                ques.get(j).setOrder(j);
            }
            Paper paper = paperDAO.findByTestIdAndUserId(testId, studentId);
            //加入到试卷里
            SinglePaperBean singlePaperBean = new SinglePaperBean();
            singlePaperBean.setQuestions(ques);
            //获取参加考试的信息
            Participate participate = participateDAO.findByUserIdAndTestId(studentId, testId);
            singlePaperBean.setClassName(participate.getClassName());
            singlePaperBean.setName(participate.getName());
            singlePaperBean.setGrade(participate.getGrade());
            if (paper != null){
                singlePaperBean.setPaperId(paper.getId());
            } else {
                singlePaperBean.setPaperId(getPaperId(testId));
            }
            singlePaperBean.setStudentId(participate.getStudentId());
            savePaper(singlePaperBean, studentId, testId); // 保持试卷
            singlePaperBeans.add(singlePaperBean);
        }

        PaperBean paperBean = new PaperBean();
        paperBean.setTestId(testId);
        paperBean.setPapers(singlePaperBeans);

        return paperBean;
    }

    /**
     * 获取考试的试卷id
     * @param testId 考试ID
     * @return 生成的试卷ID
     */
    private String getPaperId(String testId) throws PaperFullException {
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

}
