package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.infrastructure.adapter.out.persistence.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    private String status;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "comments_count")
    private int commentsCount;

    @Column(name = "impressions_count")
    private int impressionsCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public PostEntity(
            UUID id,
            String title,
            String body,
            UUID userId,
            String status,
            int likesCount,
            int commentsCount,
            int impressionsCount,
            Instant createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.impressionsCount = impressionsCount;
        this.createdAt = createdAt;
    }

    public PostEntity() {

    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
