package edu.nju.test.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paper")
public class Paper {
    private String id;
    private String userId;
    private String testId;
    private Set<TestQue> testQues = new HashSet<>();

    public Paper() {
    }

    public Paper(String id, String userId, String testId) {
        this.id = id;
        this.userId = userId;
        this.testId = testId;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TestQue> getTestQues() {
        return testQues;
    }

    public void setTestQues(Set<TestQue> testQues) {
        this.testQues = testQues;
    }
}
