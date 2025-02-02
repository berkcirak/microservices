package com.customer.auth.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Table(name = "user")
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
