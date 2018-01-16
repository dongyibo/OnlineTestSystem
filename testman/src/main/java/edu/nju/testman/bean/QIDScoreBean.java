package edu.nju.testman.bean;

import java.math.BigDecimal;

/**
 * 题目ID，内容和分值
 */
public class QIDScoreBean {
    private String id;
    private String content;
    private BigDecimal score;

    public QIDScoreBean() {
    }

    public QIDScoreBean(String id, String content, BigDecimal score) {
        this.id = id;
        this.content = content;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
