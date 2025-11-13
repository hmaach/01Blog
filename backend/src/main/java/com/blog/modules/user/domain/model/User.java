package com.blog.modules.user.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {

    private final UUID id;

    private String name;
    private String email;
    private String username;

    private String role;
    private String status;
    private String password;

    private int postsCount;
    private int subscribersCount;
    private int impressionsCount;

    private UUID avatarMediaId;
    private String avatarUrl;
    private String readme;
    private final Instant createdAt;

    public User(
            UUID id,
            String name,
            String username,
            String email,
            String password,
            String role,
            String status,
            int postsCount,
            int subscribersCount,
            int impressionsCount,
            UUID avatarMediaId,
            String avatarUrl,
            String readme,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.postsCount = postsCount;
        this.subscribersCount = subscribersCount;
        this.impressionsCount = impressionsCount;
        this.avatarMediaId = avatarMediaId;
        this.avatarUrl = avatarUrl;
        this.readme = readme;
        this.createdAt = createdAt;
    }

    public User(
            UUID id,
            String name,
            String username,
            String email,
            String password,
            String role,
            String status,
            String readme,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.readme = readme;
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

    public String getStatus() {
        return status;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public int getImpressionsCount() {
        return impressionsCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UUID getAvatarMediaId() {
        return avatarMediaId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadme() {
        return readme;
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

    public void changeReadme(String readme) {
        this.readme = readme;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
