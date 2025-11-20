package com.blog.modules.report.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Report {

    private UUID id;
    private UUID reporterId;
    private String reportedType;
    private UUID reportedUserId;
    private UUID reportedPostId;
    private UUID reportedCommentId;
    private String category;
    private String reason;
    private String status;
    private Instant createdAt;

    public Report(UUID id, UUID reporterId, String reportedType, UUID reportedUserId, UUID reportedPostId, UUID reportedCommentId,
            String category, String reason, String status, Instant createdAt) {
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

    public Report() {
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

    public String getReportedType() {
        return reportedType;
    }

    public void setReportedType(String reportedType) {
        this.reportedType = reportedType;
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

}
