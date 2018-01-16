package edu.nju.qstlib.bean;

import java.util.List;

/**
 * 题目列表结果Bean
 */
public class QuesListReBean {

    private int rcode;
    private List<QuesBean> questions;

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public List<QuesBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuesBean> questions) {
        this.questions = questions;
    }
}
