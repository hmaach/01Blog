package com.blog.modules.report.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateReportCommand(
        @NotNull(message = "Reported user ID cannot be null")
        UUID reportedUserId,
        @NotNull
        @NotBlank
        @Pattern(regexp = "USER|POST|COMMENT", message = "Reported type must be one of: USER, POST, COMMENT")
        String reportedType,
        UUID reportedPostId,
        UUID reportedCommentId,
        @NotNull
        @NotBlank
        @Size(max = 100, message = "Category must be less than 100 characters")
        String category,
        @NotNull
        @NotBlank
        @Size(max = 250, message = "Comment must be less than 250 characters")
        String reason
        ) {

}
