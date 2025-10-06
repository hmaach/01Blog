package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentCommand(
        @NotBlank(message = "Comment cannot be blank")
        @Size(max = 255, message = "Comment must be less than 255 characters")
        String comment
        ) {

}
