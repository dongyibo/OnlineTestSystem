package edu.nju.test.domain.key;


import java.io.Serializable;
import java.util.Objects;

public class TestLibKey implements Serializable{
    private String testId;
    private String quesId;

    public TestLibKey() {
    }

    public TestLibKey(String testId, String quesId) {
        this.testId = testId;
        this.quesId = quesId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestLibKey that = (TestLibKey) o;
        return Objects.equals(testId, that.testId) &&
                Objects.equals(quesId, that.quesId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(testId, quesId);
    }
}
