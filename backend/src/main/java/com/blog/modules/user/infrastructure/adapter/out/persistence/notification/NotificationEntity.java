package com.blog.modules.user.infrastructure.adapter.out.persistence.notification;

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
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_owner_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "post_owner_id")
    private UUID userOwnerId;

    private Boolean seen;

    @Column(name = "post_id")
    private UUID postId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public NotificationEntity(UUID id, UUID userId, UUID userOwnerId, UUID postId, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.userOwnerId = userOwnerId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public NotificationEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UUID getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(UUID userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

}
