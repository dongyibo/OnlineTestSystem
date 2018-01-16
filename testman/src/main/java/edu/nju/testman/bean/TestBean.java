package edu.nju.testman.bean;

import java.util.Calendar;
import java.util.List;

public class TestBean {
    private String userId; // 用户ID
    private String testId; // 考试ID
    private String testName; // 考试名
    private String libId; // 题库ID
    private List<QIDScoreBean> questions; // 题目ID和分值列表
    private Calendar startTime; // 开始时间
    private Calendar endTime; // 结束时间
    private List<StudentReBean> students; // 学生邮箱列表

    public TestBean() {
    }

    public TestBean(String userId, String testId, String testName, String libId, List<QIDScoreBean> questions, Calendar startTime, Calendar endTime, List<StudentReBean> students) {
        this.userId = userId;
        this.testId = testId;
        this.testName = testName;
        this.libId = libId;
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.students = students;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public List<QIDScoreBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QIDScoreBean> questions) {
        this.questions = questions;
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

    public List<StudentReBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentReBean> students) {
        this.students = students;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
