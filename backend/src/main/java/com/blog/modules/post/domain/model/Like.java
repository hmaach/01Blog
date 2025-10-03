package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Like {

    private final UUID id;
    private final UUID userId;
    private final UUID postId;
    private final Instant createdAt;

    public Like(UUID id, UUID userId, UUID postId, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}
