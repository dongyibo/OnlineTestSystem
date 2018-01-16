package edu.nju.statistics.bean;

import java.util.List;

public class StatisticsBean {

    private String testId;
    private String testName;
    private List<ScoreBean> scores;

    public StatisticsBean() {
    }

    public StatisticsBean(String testId, String testName, List<ScoreBean> scores) {
        this.testId = testId;
        this.testName = testName;
        this.scores = scores;
    }

    public List<ScoreBean> getScores() {
        return scores;
    }

    public void setScores(List<ScoreBean> scores) {
        this.scores = scores;
    }

    public ScoreBean get(int index) {
        return scores.get(index);
    }

    public ScoreBean set(int index, ScoreBean element) {
        return scores.set(index, element);
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
