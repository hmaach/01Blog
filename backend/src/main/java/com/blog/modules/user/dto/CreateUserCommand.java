package com.blog.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserCommand(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @Size(min = 6)
        String password,
        @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN")
        String role
        ) {

}
