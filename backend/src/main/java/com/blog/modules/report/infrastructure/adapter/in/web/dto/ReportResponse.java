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

        AuthorResponse reporterUser
                = report.getReportedUser() != null ? AuthorResponse.fromDomain(report.getReporterUser())
                : null;
        AuthorResponse reportedUser
                = report.getReportedUser() != null ? AuthorResponse.fromDomain(report.getReportedUser())
                : null;

        return new ReportResponse(
                report.getId(),
                reporterUser,
                reportedUser,
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
