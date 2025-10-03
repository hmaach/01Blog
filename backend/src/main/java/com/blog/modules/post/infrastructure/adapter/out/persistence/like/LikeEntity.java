package com.blog.modules.post.infrastructure.adapter.out.persistence.like;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
public class LikeEntity {

    @EmbeddedId
    private LikeKey id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public LikeEntity() {
    }

    public LikeEntity(UUID userId, UUID postId, Instant createdAt) {
        this.id = new LikeKey(userId, postId);
        this.createdAt = createdAt;
    }

    public LikeKey getId() {
        return id;
    }

    public void setId(LikeKey id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Embeddable
    public static class LikeKey implements Serializable {

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @Column(name = "post_id", nullable = false)
        private UUID postId;

        public LikeKey() {
        }

        public LikeKey(UUID userId, UUID postId) {
            this.userId = userId;
            this.postId = postId;
        }

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public UUID getPostId() {
            return postId;
        }

        public void setPostId(UUID postId) {
            this.postId = postId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LikeKey)) {
                return false;
            }
            LikeKey that = (LikeKey) obj;
            return Objects.equals(userId, that.userId)
                    && Objects.equals(postId, that.postId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, postId);
        }
    }
}
