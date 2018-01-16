package edu.nju.statistics.domain.key;

import java.io.Serializable;
import java.util.Objects;

public class ParticipateKey implements Serializable{
    private String userId;
    private String testId;

    public ParticipateKey() {
    }

    public ParticipateKey(String userId, String testId) {
        this.userId = userId;
        this.testId = testId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipateKey that = (ParticipateKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(testId, that.testId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, testId);
    }
}
