package edu.nju.test.bean;

import java.math.BigDecimal;
import java.util.List;

public class QuestionBean {
    private int order;
    private int type;
    private String id;
    private String description;
    private List<OptionBean> options;
    private BigDecimal score;
    private String userOption;

    public QuestionBean(){}

    public QuestionBean(int order, int type, String id, String description, List<OptionBean> options, BigDecimal score) {
        this.order = order;
        this.type = type;
        this.id = id;
        this.description = description;
        this.options = options;
        this.score=score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {

        return type;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<OptionBean> getOptions() {
        return options;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOptions(List<OptionBean> options) {
        this.options = options;
    }

    public String getUserOption() {
        return userOption;
    }

    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
