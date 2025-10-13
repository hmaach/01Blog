package com.blog.modules.user.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {

    private final UUID id;
    private String name;
    private String email;
    private String username;
    private String role;
    private String password;
    private final Instant createdAt;
    private UUID avatarMediaId;

    public User(
            UUID id,
            String name,
            String username,
            String email,
            String password,
            String role,
            UUID avatarMediaId,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.avatarMediaId = avatarMediaId;
    }

    public User(
            UUID id,
            String name,
            String username,
            String email,
            String password,
            String role,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UUID getAvatarMediaId() {
        return avatarMediaId;
    }

    // Domain actions
    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeRole(String role) {
        this.role = role;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeAvatar(UUID avatarMediaId) {
        this.avatarMediaId = avatarMediaId;
    }
}
