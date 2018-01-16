package edu.nju.statistics.bean;

import java.math.BigDecimal;
import java.util.List;

public class QuestionBean {
    private String content; // 题目名
    private BigDecimal score; // 分值
    private int order; // 序号
    private List<OptionBean> options; // 选项

    public QuestionBean() {
    }

    public QuestionBean(String content, BigDecimal score, int order, List<OptionBean> options) {
        this.content = content;
        this.score = score;
        this.order = order;
        this.options = options;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<OptionBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionBean> options) {
        this.options = options;
    }
}
