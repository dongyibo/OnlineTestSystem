package edu.nju.qstlib.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "qstlib")
public class QstLib implements Serializable {
    private String id;
    private String name;
    private String userId;
    private Set<Question> questions = new HashSet<>();

    public QstLib() {
    }

    public QstLib(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @OneToMany(mappedBy = "lib", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
