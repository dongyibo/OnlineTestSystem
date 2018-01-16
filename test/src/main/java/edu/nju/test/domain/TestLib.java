package edu.nju.test.domain;

import edu.nju.test.domain.key.TestLibKey;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "test_lib")
@IdClass(TestLibKey.class)
public class TestLib {
    private String testId;
    private String quesId;
    private BigDecimal score;
    private Test test;

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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "testId", referencedColumnName = "id", insertable = false, updatable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
