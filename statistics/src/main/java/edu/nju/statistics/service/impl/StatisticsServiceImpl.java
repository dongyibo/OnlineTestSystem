package edu.nju.statistics.service.impl;

import edu.nju.statistics.bean.ScoreBean;
import edu.nju.statistics.bean.StatisticsBean;
import edu.nju.statistics.dao.*;
import edu.nju.statistics.domain.*;
import edu.nju.statistics.exception.AuthorityInvalidException;
import edu.nju.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final AccountDAO accountDAO;
    private final TestQueDAO testQueDAO;
    private final ParticipateDAO participateDAO;
    private final PaperDAO paperDAO;
    private final TestDAO testDAO;

    @Autowired
    public StatisticsServiceImpl(AccountDAO accountDAO, TestQueDAO testQueDAO, ParticipateDAO participateDAO, PaperDAO paperDAO, TestDAO testDAO) {
        this.accountDAO = accountDAO;
        this.testQueDAO = testQueDAO;
        this.participateDAO = participateDAO;
        this.paperDAO = paperDAO;
        this.testDAO = testDAO;
    }

    @Override
    @Transactional
    public int generateStatistics(String testId) {
        List<TestQue> testQues = testQueDAO.getAllQuestions(testId);
        Map<String, BigDecimal> scoreMap = new HashMap<>();
        for (TestQue testQue:testQues) {
            String[] ops = testQue.getOption().split(",");
            boolean isRight = true;
            List<Integer> opis = new ArrayList<>(); // 转成整数，方便查找
            for (String op:ops) {
                opis.add(Integer.parseInt(op));
            }
            for (Option option:testQue.getQuestion().getOptionSet()){ // 比对选项，看是否正确
                boolean right = option.isRight();
                if (right){ // 如果是正确选项
                    if (!opis.contains(option.getId())){ // 但是没有选
                        isRight = false; // 就知道没得分，可以退出循环
                        break;
                    }
                }
            }

            if (scoreMap.containsKey(testQue.getPaperId())){
                if(isRight){ // 如果本题做对了
                    BigDecimal value = scoreMap.get(testQue.getPaperId());
                    scoreMap.put(testQue.getPaperId(), value.add(testQue.getScore())); // 就加上本题的分数
                }
                // 没做对就不用算分了
            } else { // 没有计算到这张试卷
                if (isRight){
                    scoreMap.put(testQue.getPaperId(), testQue.getScore()); // 做对了就设为本题的分数
                } else {
                    scoreMap.put(testQue.getPaperId(), BigDecimal.ZERO); // 否则设为0分
                }
            }
        }

        for (String paperId:scoreMap.keySet()){ // 更新成绩
            Paper paper = paperDAO.findById(paperId); // 获得试卷信息
            Participate participate = participateDAO.findByUserIdAndTestId(paper.getUserId(), testId);
            participate.setScore(scoreMap.get(paperId)); // 设置得分
            participateDAO.save(participate);
        }
        return 0;
    }

    /**
     * 将选项列表转为Map形式，格式为key = 题目ID， value = map(key = 序号, value = 是否正确)
     * @param options 选项列表
     * @return 选项的map
     */
    private Map<String, Map<Integer, Boolean>> transferOptionList(Set<Option> options){
        Map<String, Map<Integer, Boolean>> optionMap = new HashMap<>();
        for (Option option:options) {
            Map<Integer, Boolean> value;
            if (optionMap.containsKey(option.getQuesId())){
                value = optionMap.get(option.getQuesId());
            } else {
                value = new HashMap<>();
            }
            value.put(option.getId(), option.isRight());
            optionMap.put(option.getQuesId(), value);
        }
        return optionMap;
    }

    @Override
    public StatisticsBean checkStatistics(String testId, String userId) throws AuthorityInvalidException {
        Account account = accountDAO.findById(userId);
        if (account.getType() != 1){ // 不是教师
            throw new AuthorityInvalidException();
        } else {
            List<Participate> participates = participateDAO.findByTestId(testId);
            List<ScoreBean> scores = new ArrayList<>();
            for (Participate participate : participates) {
                ScoreBean scoreBean = new ScoreBean(participate.getUserId(), participate.getName(), participate.getStudentId(),
                        participate.getScore(), participate.getClassName(), participate.getGrade());
                scores.add(scoreBean);
            }
            Test test = testDAO.findById(testId);
            return new StatisticsBean(testId, test.getSubject(), scores);
        }
    }
}
