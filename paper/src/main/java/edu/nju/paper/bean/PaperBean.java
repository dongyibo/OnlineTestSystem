package edu.nju.paper.bean;

import java.util.List;

public class PaperBean {
    private String testId;
    private List<SinglePaperBean> papers;

    public PaperBean() {
    }

    public PaperBean(String testId, List<SinglePaperBean> papers) {
        this.testId = testId;
        this.papers = papers;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public List<SinglePaperBean> getPapers() {
        return papers;
    }

    public void setPapers(List<SinglePaperBean> papers) {
        this.papers = papers;
    }
}
