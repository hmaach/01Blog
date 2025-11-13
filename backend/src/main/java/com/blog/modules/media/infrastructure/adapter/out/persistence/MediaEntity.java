package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
public class MediaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "media_type", nullable = false)
    private String mediaType;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(nullable = false)
    private String url;

    @Column(name = "related_to", nullable = false)
    private String relatedTo;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    public MediaEntity(
            UUID id,
            UUID userId,
            String mediaType,
            String url,
            String relatedTo,
            Instant uploadedAt
    ) {
        this.id = id;
        // this.userId = userId;
        this.mediaType = mediaType;
        this.url = url;
        this.relatedTo = relatedTo;
        this.uploadedAt = uploadedAt;
    }

    public MediaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public void setUserId(UUID userId) {
        this.user.setId(userId);
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
