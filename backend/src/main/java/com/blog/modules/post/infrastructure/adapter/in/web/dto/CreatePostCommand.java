package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostCommand(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 255, message = "Title must be less than 255 characters")
        String title,
        @NotBlank(message = "Body cannot be blank")
        @Size(max = 5000, message = "Body must be less than 5000 characters")
        String body,
        List<MultipartFile> files
        ) {

}
