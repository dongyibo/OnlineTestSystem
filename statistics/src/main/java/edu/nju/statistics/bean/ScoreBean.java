package edu.nju.statistics.bean;

import java.math.BigDecimal;

public class ScoreBean {
    private String userId;
    private String name;
    private String studentId;
    private BigDecimal score;
    private String className;
    private String grade;

    public ScoreBean() {
    }

    public ScoreBean(String userId, String name, String studentId, BigDecimal score, String className, String grade) {
        this.userId = userId;
        this.name = name;
        this.studentId = studentId;
        this.score = score;
        this.className = className;
        this.grade = grade;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
