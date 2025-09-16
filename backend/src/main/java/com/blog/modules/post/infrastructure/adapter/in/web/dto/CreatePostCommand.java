package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreatePostCommand(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name,
        @NotBlank(message = "Description cannot be blank")
        String description,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be greater than 0")
        Double price
        ) {

}
