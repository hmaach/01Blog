package com.blog.modules.user.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    // @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String role;

    private String status;

    @Column(name = "posts_count")
    private int postsCount;

    @Column(name = "subscribers_count")
    private int subscribersCount;

    @Column(name = "impressions_count")
    private int impressionsCount;

    @Column(nullable = false)
    private String password;

    @Column(name = "avatar_media_id")
    private UUID avatarMediaId;

    @Column(name = "readme")
    private String readme;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public UserEntity(
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
            String readme,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.postsCount = postsCount;
        this.subscribersCount = subscribersCount;
        this.impressionsCount = impressionsCount;
        this.readme = readme;
        this.createdAt = createdAt;
    }

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public int getImpressionsCount() {
        return impressionsCount;
    }

    public void setImpressionsCount(int impressionsCount) {
        this.impressionsCount = impressionsCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getAvatarMediaId() {
        return avatarMediaId;
    }

    public void setAvatarMediaId(UUID avatarMediaId) {
        this.avatarMediaId = avatarMediaId;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }
}
