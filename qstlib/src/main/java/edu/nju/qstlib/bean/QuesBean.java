package edu.nju.qstlib.bean;

import java.util.List;

/**
 * 题目信息Bean
 */
public class QuesBean {
    private String questionId;
    private String questionContent;
    private List<OptionBean> options;

    public QuesBean() {
    }

    /**
     * 题目信息Bean
     * @param questionId 题目ID
     * @param questionContent 题目内容
     * @param options 题目选项
     */
    public QuesBean(String questionId, String questionContent, List<OptionBean> options) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.options = options;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public List<OptionBean> getOptions() {
        return options;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setOptions(List<OptionBean> options) {
        this.options = options;
    }
}
