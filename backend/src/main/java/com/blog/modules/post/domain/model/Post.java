package com.blog.modules.post.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Post {

    private final UUID id;
    private final UUID userId;
    private String title;
    private String body;
    private String status;
    private final Instant createdAt;

    public Post(
            UUID id,
            String title,
            String body,
            UUID userId,
            String status,
            Instant createdAt
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // public Post(
    //         String id,
    //         String name,
    //         String description,
    //         Double price,
    //         String userId,
    //         Instant createdAt
    // ) {
    //     this.id = id;
    //     this.name = name;
    //     this.description = description;
    //     this.price = price;
    //     this.userId = userId;
    //     this.createdAt = createdAt;
    // }

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
