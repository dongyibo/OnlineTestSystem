package edu.nju.test.bean;

public class ReTestResultBean {
    private int rcode;
    private TestResultBean testResult;

    public ReTestResultBean(int rcode, TestResultBean testResult) {
        this.rcode = rcode;
        this.testResult = testResult;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public TestResultBean getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResultBean testResult) {
        this.testResult = testResult;
    }
}
