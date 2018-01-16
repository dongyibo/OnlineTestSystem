package edu.nju.testman.bean;

public class StudentBean {
    private String email;
    private String name;
    private String studentId;
    private String grade;
    private String className;

    public StudentBean(String email, String name, String studentId, String grade, String className) {
        this.email = email;
        this.name = name;
        this.studentId = studentId;
        this.grade = grade;
        this.className = className;
    }

    public StudentBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
