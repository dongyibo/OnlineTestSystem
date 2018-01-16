package edu.nju.test.domain;

import edu.nju.test.domain.key.OptionKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "`option`")
@IdClass(OptionKey.class)
public class Option implements Serializable{
    private int id;
    private String quesId;
    private String name;
    private boolean isRight;
    private Question question;

    public Option() {
    }

    public Option(int id, String quesId, String name, boolean isRight) {
        this.id = id;
        this.quesId = quesId;
        this.name = name;
        this.isRight = isRight;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "isRight")
    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }


    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "quesId", referencedColumnName = "id", insertable = false, updatable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
