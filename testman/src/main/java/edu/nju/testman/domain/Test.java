package edu.nju.testman.domain;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "test")
public class Test {
    private String id;
    private String libId;
    private String creator;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;
    private int quesNum;
    private String subject;
    private Set<TestLib> libSet = new HashSet<>();
    private Set<Participate> participateSet = new HashSet<>();


    public Test() {
    }

    public Test(String id, String libId, String creator, Calendar startTime, Calendar endTime, int quesNum, String subject) {
        this.id = id;
        this.libId = libId;
        this.creator = creator;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quesNum = quesNum;
        this.subject = subject;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public int getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(int quesNum) {
        this.quesNum = quesNum;
    }

    @Column(name = "`subject`")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TestLib> getLibSet() {
        return libSet;
    }

    public void setLibSet(Set<TestLib> libSet) {
        this.libSet = libSet;
    }

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Participate> getParticipateSet() {
        return participateSet;
    }

    public void setParticipateSet(Set<Participate> participateSet) {
        this.participateSet = participateSet;
    }
}
