package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Post {

    private final UUID id;
    private final UUID userId;
    private String title;
    private String body;
    private String status;
    private int likesCount;
    private int commentsCount;
    private final Instant createdAt;

    public Post(
            UUID id,
            UUID userId,
            String title,
            String body,
            String status,
            int likesCount,
            int commentsCount,
            Instant createdAt
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
    }

    // --- Getters ---
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    // --- Domain actions ---
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateBody(String body) {
        this.body = body;
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
