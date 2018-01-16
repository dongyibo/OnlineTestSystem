package edu.nju.testman.bean;

import java.util.List;

public class TestListBean {
    private int rcode;
    private List<TestNameBean> tests;

    public TestListBean() {
    }

    public TestListBean(int rcode, List<TestNameBean> tests) {
        this.rcode = rcode;
        this.tests = tests;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public List<TestNameBean> getTests() {
        return tests;
    }

    public void setTests(List<TestNameBean> tests) {
        this.tests = tests;
    }
}
