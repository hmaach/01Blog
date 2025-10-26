package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record UserProfileResponse(
        UUID id,
        String name,
        String username,
        String role,
        String status,
        int postsCount,
        int subscribersCount,
        int impressionsCount,
        String relation,
        String avatarUrl,
        Instant createdAt
        ) {

    public static UserProfileResponse fromDomain(User user, String avatarUrl, String relation) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getStatus(),
                user.getPostsCount(),
                user.getSubscribersCount(),
                user.getImpressionsCount(),
                relation,
                avatarUrl,
                user.getCreatedAt()
        );
    }
}
