package edu.nju.testman.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    private String id;
    private String libId;
    private String content;
    private Set<TestLib> testLibs = new HashSet<>();

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

    @OneToMany(mappedBy = "question", cascade = CascadeType.DETACH)
    public Set<TestLib> getTestLibs() {
        return testLibs;
    }

    public void setTestLibs(Set<TestLib> testLibs) {
        this.testLibs = testLibs;
    }
}
