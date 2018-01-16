package edu.nju.test.bean;

import java.util.Calendar;

public class TestBean {
    private String testId;// 考试id
    private String testName; // 考试名
    private int quesNum; // 题目总数
    private QuestionBean question; // 第一道题
    private Calendar startTime; // 开始时间
    private Calendar endTime; // 结束时间

    public TestBean(String testId, String testName, int quesNum, QuestionBean question, Calendar startTime, Calendar endTime) {
        this.testId = testId;
        this.testName = testName;
        this.quesNum = quesNum;
        this.question = question;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public int getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(int quesNum) {
        this.quesNum = quesNum;
    }
}