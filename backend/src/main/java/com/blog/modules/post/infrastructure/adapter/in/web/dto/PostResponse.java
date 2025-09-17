package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.domain.model.Post;

public record PostResponse(
        UUID id,
        String title,
        String body,
        UUID userId,
        String status,
        Instant createdAt
) {

    public static PostResponse fromDomain(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getUserId(),
                post.getStatus(),
                post.getCreatedAt()
        );
    }
}
