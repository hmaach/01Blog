package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserCommand(
        @Size(max = 50)
        String name,
        @Email
        @Size(max = 100)
        String email,
        MultipartFile avatar
        ) {

}
