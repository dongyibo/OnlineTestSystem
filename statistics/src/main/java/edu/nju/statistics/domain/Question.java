package edu.nju.statistics.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    private String id;
    private String libId;
    private String content;
    private Set<TestQue> testQueSet = new HashSet<>();
    private Set<Option> optionSet = new HashSet<>();

    public Question() {
    }

    public Question(String id, String libId, String content) {
        this.id = id;
        this.libId = libId;
        this.content = content;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    public Set<TestQue> getTestQueSet() {
        return testQueSet;
    }

    public void setTestQueSet(Set<TestQue> testQueSet) {
        this.testQueSet = testQueSet;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Option> getOptionSet() {
        return optionSet;
    }

    public void setOptionSet(Set<Option> optionSet) {
        this.optionSet = optionSet;
    }
}
