package edu.nju.qstlib.bean;

/**
 * 题目结果Bean
 */
public class QuesReBean {
    private int rcode;
    private QuesBean question;

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public QuesBean getQuestion() {
        return question;
    }

    public void setQuestion(QuesBean question) {
        this.question = question;
    }
}
