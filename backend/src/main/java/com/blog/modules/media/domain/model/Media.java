package com.blog.modules.media.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Media {

    private UUID id;
    private UUID userId;
    private String type;
    private String url;
    private long size;
    private String relatedTo;
    private Instant uploadedAt;

    public Media(
            UUID id,
            UUID userId,
            String url,
            String type,
            long size,
            String relatedTo,
            Instant uploadedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.size = size;
        this.url = url;
        this.relatedTo = relatedTo;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

}
