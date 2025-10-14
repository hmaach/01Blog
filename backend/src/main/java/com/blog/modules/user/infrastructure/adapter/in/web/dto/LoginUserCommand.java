package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserCommand(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
        ) {

}
