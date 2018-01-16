package edu.nju.testman.domain;

import edu.nju.testman.domain.key.ParticipateKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "participate")
@IdClass(ParticipateKey.class)
public class Participate implements Serializable{
    private String userId;
    private String testId;
    private String email;
    private String name;
    private String studentId;
    private BigDecimal score;
    private String className;
    private String grade;
    private String password;
    private Test test;

    public Participate() {
    }

    public Participate(String userId, String testId, String email, String name, String studentId, BigDecimal score,
                       String className, String grade, String password) {
        this.userId = userId;
        this.testId = testId;
        this.email = email;
        this.name = name;
        this.studentId = studentId;
        this.score = score;
        this.className = className;
        this.grade = grade;
        this.password = password;
    }

    @Id
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Id
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "testId", referencedColumnName = "id", insertable = false, updatable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
