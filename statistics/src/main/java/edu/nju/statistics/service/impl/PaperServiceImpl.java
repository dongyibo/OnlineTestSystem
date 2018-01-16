package edu.nju.statistics.service.impl;

import edu.nju.statistics.bean.OptionBean;
import edu.nju.statistics.bean.PaperBean;
import edu.nju.statistics.bean.QuestionBean;
import edu.nju.statistics.bean.SinglePaperBean;
import edu.nju.statistics.dao.AccountDAO;
import edu.nju.statistics.dao.ParticipateDAO;
import edu.nju.statistics.dao.TestQueDAO;
import edu.nju.statistics.domain.*;
import edu.nju.statistics.exception.AuthorityInvalidException;
import edu.nju.statistics.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PaperServiceImpl implements PaperService {

    private final AccountDAO accountDAO;
    private final TestQueDAO testQueDAO;
    private final ParticipateDAO participateDAO;

    @Autowired
    public PaperServiceImpl(AccountDAO accountDAO, TestQueDAO testQueDAO, ParticipateDAO participateDAO) {
        this.accountDAO = accountDAO;
        this.testQueDAO = testQueDAO;
        this.participateDAO = participateDAO;
    }

    @Override
    public PaperBean generatePapers(String testId, String teacher, List<String> students) throws AuthorityInvalidException {
        Account account = accountDAO.findById(teacher);
        if (account.getType() != 1){ // 不是教师，无权限
            throw new AuthorityInvalidException();
        } else {
            PaperBean paperBean = new PaperBean();
            paperBean.setTestId(testId);
            List<SinglePaperBean> singlePaperList = new ArrayList<>();
            for (String student : students) {
                SinglePaperBean singlePaper = new SinglePaperBean();
                // 设置考生基本信息
                Participate participate = participateDAO.findByUserIdAndTestId(student, testId);
                singlePaper.setStudentId(participate.getStudentId());
                singlePaper.setClassName(participate.getClassName());
                singlePaper.setGrade(participate.getGrade());
                singlePaper.setName(participate.getName());
                singlePaper.setScore(participate.getScore());
                // 获得题目信息
                List<TestQue> testQues = testQueDAO.getUserQuestions(student, testId);
                singlePaper.setPaperId(testQues.get(0).getPaperId());
                List<QuestionBean> questionList = new ArrayList<>();
                for (TestQue question : testQues) {
                    // 设置选项信息
                    Set<Option> options = question.getQuestion().getOptionSet();
                    List<OptionBean> optionList = new ArrayList<>();
                    for (Option option : options) {
                        OptionBean optionBean = new OptionBean();
                        optionBean.setId(option.getId());
                        optionBean.setName(option.getName());
                        optionBean.setSelected(isSelected(question.getOption(), option.getId()));
                        optionList.add(optionBean);
                    }
                    // 设置问题信息
                    QuestionBean questionBean = new QuestionBean();
                    questionBean.setOptions(optionList);
                    questionBean.setContent(question.getQuestion().getContent());
                    questionBean.setOrder(question.getOrder());
                    questionBean.setScore(question.getScore());
                    questionList.add(questionBean);
                }
                singlePaper.setQuestions(questionList);
                singlePaperList.add(singlePaper);
            }
            paperBean.setPapers(singlePaperList);
            return paperBean;
        }
    }

    /**
     * 一个选项是被选中
     * @param selects 选择的答案序列
     * @param id 被判断的选项的ID
     * @return 是否被选中
     */
    private boolean isSelected(String selects, int id){
        if (selects == null || selects.equals("")){
            return false;
        }
        String[] selectArray = selects.split(",");
        boolean isSelected = false;
        for (String s:selectArray){
            if (Integer.parseInt(s) == id){
                isSelected = true;
                break;
            }
        }
        return isSelected;
    }
}
