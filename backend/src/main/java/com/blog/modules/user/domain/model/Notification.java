package com.blog.modules.user.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Notification {

    private UUID id;
    private UUID userId;
    private User user;
    private UUID userOwnerId;
    private UUID postId;
    private Boolean seen;
    private Instant createdAt;

    public Notification(UUID id, UUID userId, User user, UUID postId, Boolean seen, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.user = user;
        this.postId = postId;
        this.seen = seen;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(UUID userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

}
