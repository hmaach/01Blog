package com.blog.modules.post.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_media")
public class PostMediaEntity {

    @Id
    @Column(name = "post_id", nullable = false)
    private UUID postId;
    
    @Id
    @Column(name = "media_id", nullable = false)
    private UUID mediaId;

    @Column(name = "created_at")
    private Instant createdAt;

    public PostMediaEntity() {
    }

    public PostMediaEntity(UUID postId, UUID mediaId, Instant createdAt) {
        this.postId = postId;
        this.mediaId = mediaId;
        this.createdAt = createdAt;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getMediaId() {
        return mediaId;
    }

    public void setMediaId(UUID mediaId) {
        this.mediaId = mediaId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
