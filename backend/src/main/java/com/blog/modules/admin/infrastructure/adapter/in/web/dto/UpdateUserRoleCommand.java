package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Pattern;

public record UpdateUserRoleCommand(
        @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN")
        String role
        ) {

}
