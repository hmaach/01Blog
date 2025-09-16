package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

import com.blog.modules.post.domain.model.Post;

public record PostResponse(
        String id,
        String name,
        String description,
        Double price,
        String userId,
        LocalDateTime createdAt
        ) {

    public static PostResponse fromDomain(Post post) {
        return new PostResponse(
                post.getId(),
                post.getName(),
                post.getDescription(),
                post.getPrice(),
                post.getUserId(),
                post.getCreatedAt()
        );
    }
}
