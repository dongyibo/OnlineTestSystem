package edu.nju.paper.bean;

public class PaperReBean {
    private int rcode;
    private PaperBean paper;

    public PaperReBean() {
    }

    public PaperReBean(int rcode, PaperBean paper) {
        this.rcode = rcode;
        this.paper = paper;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public PaperBean getPaper() {
        return paper;
    }

    public void setPaper(PaperBean paper) {
        this.paper = paper;
    }
}
