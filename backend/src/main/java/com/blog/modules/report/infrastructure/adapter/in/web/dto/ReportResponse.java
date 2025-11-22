package com.blog.modules.report.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.infrastructure.adapter.in.web.dto.AuthorResponse;
import com.blog.modules.report.domain.model.Report;

public record ReportResponse(
        UUID id,
        AuthorResponse reporterUser,
        AuthorResponse reportedUser,
        String reportedType,
        UUID reportedPostId,
        UUID reportedCommentId,
        String category,
        String reason,
        String status,
        Instant createdAt
        ) {

    public static ReportResponse fromDomain(Report report) {
        return new ReportResponse(
                report.getId(),
                AuthorResponse.fromDomain(report.getReporterUser()),
                AuthorResponse.fromDomain(report.getReportedUser()),
                report.getReportedType(),
                report.getReportedPostId(),
                report.getReportedCommentId(),
                report.getCategory(),
                report.getReason(),
                report.getStatus(),
                report.getCreatedAt()
        );
    }

}
