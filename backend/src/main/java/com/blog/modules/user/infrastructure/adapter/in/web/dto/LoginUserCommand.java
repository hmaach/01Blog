package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserCommand(
        @Email
        @NotBlank
        @Size(max = 100)
        String email,
        @NotBlank
        @Size(min = 6, max = 50)
        String password
        ) {

}
