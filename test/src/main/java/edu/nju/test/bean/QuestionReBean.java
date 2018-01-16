package edu.nju.test.bean;

public class QuestionReBean {
    private int rcode;
    private QuestionBean question;

    public QuestionReBean() {
    }

    public QuestionReBean(int rcode, QuestionBean question) {
        this.rcode = rcode;
        this.question = question;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }
}
