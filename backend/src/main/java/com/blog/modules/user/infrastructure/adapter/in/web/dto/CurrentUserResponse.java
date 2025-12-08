package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record CurrentUserResponse(
        UUID id,
        String name,
        String username,
        String email,
        Boolean emailVerified,
        String role,
        String avatarUrl
        ) {

    public static CurrentUserResponse fromDomain(User user) {
        return new CurrentUserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getEmailVerified(),
                user.getRole(),
                user.getAvatarUrl()
        );
    }

    public static CurrentUserResponse fromDomain(User user, String avatarUrl) {
        return new CurrentUserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getEmailVerified(),
                user.getRole(),
                avatarUrl
        );
    }
}
