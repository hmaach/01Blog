package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Like {

    private final UUID userId;
    private final UUID postId;
    private final Instant createdAt;

    public Like(UUID userId, UUID postId, Instant createdAt) {
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
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
