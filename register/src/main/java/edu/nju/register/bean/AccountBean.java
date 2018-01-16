package edu.nju.register.bean;

public class AccountBean {
    private String id;
    private String username;
    private String email;
    private int type;

    public AccountBean(String id, String username, String email, int type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public AccountBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
