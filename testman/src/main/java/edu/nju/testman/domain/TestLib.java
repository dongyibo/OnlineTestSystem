package edu.nju.testman.domain;

import edu.nju.testman.domain.key.TestLibKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "test_lib")
@IdClass(TestLibKey.class)
public class TestLib implements Serializable{
    private String testId;
    private String quesId;
    private BigDecimal score;
    private Test test;
    private Question question;

    public TestLib() {
    }

    public TestLib(String testId, String quesId, BigDecimal score) {
        this.testId = testId;
        this.quesId = quesId;
        this.score = score;
    }

    @Id
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Id
    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testId", referencedColumnName = "id", insertable = false, updatable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quesId", referencedColumnName = "id", insertable = false, updatable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
