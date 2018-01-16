package edu.nju.test.bean;

import java.math.BigDecimal;

public class TestResultBean {
    private String userId;
    private String testId;
    private BigDecimal score;

    public TestResultBean(String userId, String testId, BigDecimal score) {
        this.userId = userId;
        this.testId = testId;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
