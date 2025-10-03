package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Comment {

    private final UUID id;
    private final UUID userId;
    private final UUID postId;
    private final String text;
    private final Instant createdAt;

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
}
