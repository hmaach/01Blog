package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.blog.modules.media.infrastructure.adapter.out.persistence.MediaEntity;
import com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia.PostMediaEntity;
import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "id.postId", fetch = FetchType.LAZY)
    private List<PostMediaEntity> postMedias;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    private String status;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "comments_count")
    private int commentsCount;

    @Column(name = "impressions_count")
    private int impressionsCount;

    @Transient
    private String firstMediaUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters
    public PostEntity(
            UUID id,
            String title,
            String body,
            UUID userId,
            String status,
            int likesCount,
            int commentsCount,
            int impressionsCount,
            Instant createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.impressionsCount = impressionsCount;
        this.createdAt = createdAt;
    }

    public PostEntity() {

    }

    public List<MediaEntity> getMedias() {
        return Collections.emptyList();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getImpressionsCount() {
        return impressionsCount;
    }

    public void setImpressionsCount(int impressionsCount) {
        this.impressionsCount = impressionsCount;
    }

    public UserEntity getUser() {
        return user;
    }

    // public List<MediaEntity> getMedias() {
    //     return medias;
    // }
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<PostMediaEntity> getPostMedias() {
        return postMedias;
    }

    public void setPostMedias(List<PostMediaEntity> postMedias) {
        this.postMedias = postMedias;
    }

    public String getFirstMediaUrl() {
        return firstMediaUrl;
    }

    public void setFirstMediaUrl(String firstMediaUrl) {
        this.firstMediaUrl = firstMediaUrl;
    }

}
