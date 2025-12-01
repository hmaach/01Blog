package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record UserResponse(
        UUID id,
        String name,
        String username,
        String email,
        String role,
        int postsCount,
        String avatarUrl,
        Instant createdAt
        ) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getPostsCount(),
                user.getAvatarUrl(),
                user.getCreatedAt()
        );
    }
}
