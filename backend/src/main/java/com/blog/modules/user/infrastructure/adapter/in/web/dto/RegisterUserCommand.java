package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserCommand(
        @NotBlank
        @Size(max = 30)
        String name,
        @Email
        @NotBlank
        @Size(max = 100)
        String email,
        @NotBlank
        @Size(min = 6, max = 50)
        String password,
        MultipartFile avatar
        ) {

}
