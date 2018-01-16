package edu.nju.qstlib.service;

import edu.nju.qstlib.bean.QuesBean;

import java.util.List;

/**
 * 题目相关操作Service
 */
public interface QuestionService {
    /**
     * 获得题目列表
     * @param libId 题库ID
     * @return 题目列表
     */
    public List<QuesBean> getQuestionList(String libId);

    /**
     * 获得题目信息
     * @param questionId 题目ID
     * @return 题目Bean
     */
    public QuesBean getQuestion(String questionId);

    /**
     * 修改题目内容
     * @param quesBean 题目信息
     * @return 题目列表
     */
    public List<QuesBean> changeQuestion(String questionId, QuesBean quesBean);

    /**
     * 删除题目
     * @param questionId 题目ID
     * @return 题目列表
     */
    public List<QuesBean> deleteQuestion(String questionId);
}
