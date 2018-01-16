package edu.nju.test.bean;

import java.util.List;

/**
 * 用户答案bean
 */
public class AnswerBean {
    private String id; // 问题ID
    private List<Integer> options; // 选择的答案

    public AnswerBean() {
    }

    public AnswerBean(String id, List<Integer> options) {
        this.id = id;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }
}
