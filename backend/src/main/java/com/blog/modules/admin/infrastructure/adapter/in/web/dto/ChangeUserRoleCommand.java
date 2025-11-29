package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeUserRoleCommand(
        @NotNull
        @Pattern(regexp = "ADMIN|USER", message = "Role must be 'ADMIN' or 'USER'")
        String role
        ) {

}
