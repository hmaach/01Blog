package com.blog.modules.report.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "reporter_id", nullable = false)
    private UUID reporterId;

    @Column(name = "reported_type", nullable = false)
    private String reportedType;

    @Column(name = "reported_user_id", nullable = false)
    private UUID reportedUserId;

    @Column(name = "reported_post_id")
    private UUID reportedPostId;

    @Column(name = "reported_comment_id")
    private UUID reportedCommentId;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ReportEntity(UUID id, UUID reporterId, String reportedType, UUID reportedUserId, UUID reportedPostId,
            UUID reportedCommentId, String category, String reason, String status, Instant createdAt) {
        this.id = id;
        this.reporterId = reporterId;
        this.reportedType = reportedType;
        this.reportedUserId = reportedUserId;
        this.reportedPostId = reportedPostId;
        this.reportedCommentId = reportedCommentId;
        this.category = category;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ReportEntity() {
        //TODO Auto-generated constructor stub
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getReporterId() {
        return reporterId;
    }

    public void setReporterId(UUID reporterId) {
        this.reporterId = reporterId;
    }

    public UUID getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(UUID reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public UUID getReportedPostId() {
        return reportedPostId;
    }

    public void setReportedPostId(UUID reportedPostId) {
        this.reportedPostId = reportedPostId;
    }

    public UUID getReportedCommentId() {
        return reportedCommentId;
    }

    public void setReportedCommentId(UUID reportedCommentId) {
        this.reportedCommentId = reportedCommentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getReportedType() {
        return reportedType;
    }

    public void setReportedType(String reportedType) {
        this.reportedType = reportedType;
    }
}
