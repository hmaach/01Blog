package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_media")
public class PostMediaEntity {

    @EmbeddedId
    private PostMediaKey id;

    @Column(name = "created_at")
    private Instant createdAt;

    public PostMediaEntity() {
    }

    public PostMediaEntity(UUID postId, UUID mediaId, Instant createdAt) {
        this.id = new PostMediaKey(postId, mediaId);
        this.createdAt = createdAt;
    }

    public PostMediaKey getId() {
        return id;
    }

    public void setId(PostMediaKey id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Embeddable
    public static class PostMediaKey implements Serializable {

        @Column(name = "post_id", nullable = false)
        private UUID postId;

        @Column(name = "media_id", nullable = false)
        private UUID mediaId;

        public PostMediaKey() {
        }

        public PostMediaKey(UUID postId, UUID mediaId) {
            this.postId = postId;
            this.mediaId = mediaId;
        }

        public UUID getPostId() {
            return postId;
        }

        public UUID getMediaId() {
            return mediaId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostMediaKey)) {
                return false;
            }
            PostMediaKey that = (PostMediaKey) obj;
            return Objects.equals(postId, that.postId)
                    && Objects.equals(mediaId, that.mediaId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(postId, mediaId);
        }
    }

}
