package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangePostStatusCommand(
        @NotNull
        @Pattern(regexp = "PUBLISHED|HIDDEN", message = "Status must be 'PUBLISHED' or 'HIDDEN'")
        String status
        ) {

}
