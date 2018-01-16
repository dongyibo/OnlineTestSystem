package edu.nju.test.bean;

public class ReTestBean {
    private int rcode;
    private TestBean test;

    public ReTestBean(int rcode, TestBean test) {
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
