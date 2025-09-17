package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserCommand(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 3, max = 20)
        String username,
        @NotBlank
        @Size(min = 6)
        String password,
        UUID avatarMediaId
        ) {

}
