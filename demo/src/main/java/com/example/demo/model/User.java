package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String type;

    public User() {
    }

    public User(Integer id, String name, String password, String email, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
