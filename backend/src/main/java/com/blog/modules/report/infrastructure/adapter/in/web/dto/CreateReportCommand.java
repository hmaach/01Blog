package com.blog.modules.report.infrastructure.adapter.in.web.dto;

import org.hibernate.validator.constraints.UUID;

public record CreateReportCommand(
        UUID reported,
        UUID reportedUserId,
        UUID reportedPostId,
        UUID reportedCommentId,
        String category,
        String reason
        ) {

}
