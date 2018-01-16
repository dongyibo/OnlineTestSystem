package edu.nju.testman.bean;

public class TestReBean {
    private int rcode;
    private TestBean test;

    public TestReBean() {
    }

    public TestReBean(int rcode, TestBean test) {
        this.rcode = rcode;
        this.test = test;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public TestBean getTest() {
        return test;
    }

    public void setTest(TestBean test) {
        this.test = test;
    }
}
