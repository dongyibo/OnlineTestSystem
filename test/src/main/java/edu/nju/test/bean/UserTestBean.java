package edu.nju.test.bean;


import java.util.List;

public class UserTestBean {
    private String userId;
    private String testId;
    private List<AnswerBean> userOptions;

    public UserTestBean() {
    }

    public UserTestBean(String userId, String testId, List<AnswerBean> userOptions) {
        this.userId = userId;
        this.testId = testId;
        this.userOptions = userOptions;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public List<AnswerBean> getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(List<AnswerBean> userOptions) {
        this.userOptions = userOptions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
