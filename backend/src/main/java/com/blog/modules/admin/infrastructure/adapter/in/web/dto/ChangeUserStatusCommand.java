package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeUserStatusCommand(
        @NotNull
        @Pattern(regexp = "ACTIVE|BANNED", message = "Status must be 'ACTIVE' or 'BANNED'")
        String status
        ) {

}
