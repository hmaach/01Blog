package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public class Comment {

    private final UUID id;
    private final UUID userId;
    private final UUID postId;
    private final String text;
    private final Instant createdAt;
    private User user;

    public Comment(UUID id, User user, UUID userId, UUID postId, String text, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(UUID id, UUID userId, UUID postId, String text, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
