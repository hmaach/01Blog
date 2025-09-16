package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserCommand(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @Size(min = 6)
        String password
        ) {

}
