package com.letsplay.domain.model;

import java.time.LocalDateTime;

public class User {

    private final String id;
    private String name;
    private String email;
    private String role;
    private String password;
    private final LocalDateTime createdAt;

    public User(String id, String name, String email, String password, String role, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Domain actions
    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(String role) {
        this.role = role;
    }

    public void changePassword(String encode) {
        this.password = encode;
    }
}
