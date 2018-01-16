package edu.nju.test.domain.key;

import java.io.Serializable;
import java.util.Objects;

public class TestQueKey implements Serializable{
    private String paperId;
    private String quesId;

    public TestQueKey() {
    }

    public TestQueKey(String paperId, String quesId) {
        this.paperId = paperId;
        this.quesId = quesId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
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
        TestQueKey that = (TestQueKey) o;
        return Objects.equals(paperId, that.paperId) &&
                Objects.equals(quesId, that.quesId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(paperId, quesId);
    }
}
