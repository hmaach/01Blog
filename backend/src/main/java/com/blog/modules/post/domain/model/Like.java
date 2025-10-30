package com.blog.modules.post.domain.model;

import java.util.UUID;

public class Like {

    private final UUID userId;
    private final UUID postId;

    public Like(UUID userId, UUID postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }
}
