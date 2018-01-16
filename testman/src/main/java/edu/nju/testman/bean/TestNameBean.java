package edu.nju.testman.bean;

import java.util.Calendar;

public class TestNameBean {
    private String id;
    private String name;
    private Calendar startTime;
    private Calendar endTime;

    public TestNameBean() {
    }

    public TestNameBean(String id, String name,Calendar startTime,Calendar endTime) {
        this.id = id;
        this.name = name;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
