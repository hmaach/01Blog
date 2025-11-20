package com.blog.modules.report.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import com.blog.modules.report.domain.model.Report;

public record ReportResponse(
        UUID id,
        String reportedType,
        UUID reportedUserId,
        UUID reportedPostId,
        UUID reportedCommentId,
        String category,
        String reason
        ) {

    public static ReportResponse fromDomain(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getReportedType(),
                report.getReportedUserId(),
                report.getReportedPostId(),
                report.getReportedCommentId(),
                report.getCategory(),
                report.getReason()
        );
    }

}
