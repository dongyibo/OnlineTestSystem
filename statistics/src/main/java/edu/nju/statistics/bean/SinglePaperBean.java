package edu.nju.statistics.bean;

import java.math.BigDecimal;
import java.util.List;

public class SinglePaperBean {
    private String paperId;
    private String name;
    private String studentId;
    private String className;
    private String grade;
    private BigDecimal score;
    private List<QuestionBean> questions;

    public SinglePaperBean() {
    }

    public SinglePaperBean(String paperId, String name, String studentId, String className, String grade, BigDecimal score, List<QuestionBean> questions) {
        this.paperId = paperId;
        this.name = name;
        this.studentId = studentId;
        this.className = className;
        this.grade = grade;
        this.score = score;
        this.questions = questions;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public List<QuestionBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionBean> questions) {
        this.questions = questions;
    }
}
