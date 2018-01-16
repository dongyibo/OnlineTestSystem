package edu.nju.qstlib.service.impl;

import edu.nju.qstlib.bean.OptionBean;
import edu.nju.qstlib.bean.QuesBean;
import edu.nju.qstlib.dao.QuestionDAO;
import edu.nju.qstlib.domain.Option;
import edu.nju.qstlib.domain.Question;
import edu.nju.qstlib.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO;

    @Autowired
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public List<QuesBean> getQuestionList(String libId) {
        List<Question> questions = questionDAO.findByLibId(libId);
        List<QuesBean> quesListBeans = new ArrayList<>();
        for (Question q:questions) {
            List<OptionBean> optionBeanList = new ArrayList<>();
            for (Option o:q.getOptions()){
                OptionBean optionBean = new OptionBean(o.getId(), o.getName(), o.isRight());
                optionBeanList.add(optionBean);
            }
            QuesBean bean = new QuesBean(q.getId(), q.getContent(), optionBeanList);
            quesListBeans.add(bean);
        }
        return quesListBeans;
    }

    @Override
    public QuesBean getQuestion(String questionId) {
        Question q = questionDAO.findById(questionId);
        List<OptionBean> optionBeans = new ArrayList<>();
        for (Option op:q.getOptions()) {
            OptionBean bean = new OptionBean(op.getId(), op.getName(), op.isRight());
            optionBeans.add(bean);
        }
        return new QuesBean(q.getId(), q.getContent(), optionBeans);
    }

    @Override
    public List<QuesBean> changeQuestion(String questionId, QuesBean quesBean) {
        Question question = questionDAO.findById(questionId);
        String libId = question.getLibId();
        question = new Question(questionId, libId, quesBean.getQuestionContent());
        Set<Option> optionSet = new HashSet<>();
        for (OptionBean bean:quesBean.getOptions()) {
            Option op = new Option(bean.getOptionId(), questionId, bean.getOptionContent(), bean.isRight());
            optionSet.add(op);
        }
        question.setOptions(optionSet);
        questionDAO.save(question);
        return getQuestionList(libId);
    }

    @Override
    public List<QuesBean> deleteQuestion(String questionId) {
        Question question = questionDAO.findById(questionId);
        String libId = question.getLibId();
        questionDAO.delete(questionId);
        return getQuestionList(libId);
    }
}
