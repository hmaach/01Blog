package com.blog.modules.media.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Media {

    private UUID id;
    private UUID userId;
    private String type;
    private String url;
    private Instant uploadedAt;

    public Media(UUID id, UUID userId, String url, String type, Instant uploadedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.url = url;
        this.uploadedAt = uploadedAt;
    }

    public Media() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setMediaType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

}
