package edu.nju.paper.domain;

import edu.nju.paper.domain.key.TestQueKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "test_que")
@IdClass(TestQueKey.class)
public class TestQue implements Serializable, Comparable{
    private String paperId;
    private String quesId;
    private int order;
    private BigDecimal score;
    private String option;
    private Paper paper;
    private Question question;

    public TestQue() {
    }

    public TestQue(String paperId, String quesId, int order, BigDecimal score, String option) {
        this.paperId = paperId;
        this.quesId = quesId;
        this.order = order;
        this.score = score;
        this.option = option;
    }

    @Id
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Id
    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    @Column(name = "`order`")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Column(name = "`option`")
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "paperId", referencedColumnName = "id", insertable = false, updatable = false)
    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quesId", referencedColumnName = "id", insertable = false, updatable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null){
            throw new NullPointerException();
        }
        if (! (o instanceof TestQue)){
            throw new ClassCastException();
        }
        TestQue t = (TestQue) o;
        return Integer.compare(this.order, t.order);
    }
}
