package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record CurrentUserResponse(
        UUID id,
        String name,
        String username,
        String role,
        String avatarUrl
        ) {

    public static CurrentUserResponse fromDomain(User user, String avatarUrl) {
        return new CurrentUserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                avatarUrl
        );
    }
}
