package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record UserProfileResponse(
        UUID id,
        String name,
        String username,
        String role,
        String relation,
        String status,
        int postsCount,
        int subscribersCount,
        int impressionsCount,
        String avatarUrl,
        Instant createdAt
        ) {

    public static UserProfileResponse fromDomain(User user, String avatarUrl, String relation) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                relation,
                "",
                0,
                0,
                0,
                avatarUrl,
                user.getCreatedAt()
        );
    }
}
