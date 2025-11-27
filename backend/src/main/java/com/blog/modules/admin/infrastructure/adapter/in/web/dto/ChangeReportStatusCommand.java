package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeReportStatusCommand(
        @NotNull
        @Pattern(regexp = "pending|accepted|rejected", message = "Role must be 'pending', 'accepted' or 'rejected'")
        String status
        ) {

}
