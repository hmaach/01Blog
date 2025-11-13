package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record AuthorResponse(
        UUID id,
        String name,
        String username,
        String avatarUrl
        ) {

    public static AuthorResponse fromDomain(User user, String avatarUrl) {
        return new AuthorResponse(user.getId(), user.getName(), user.getUsername(), avatarUrl);
    }

    public static AuthorResponse fromDomain(User user) {
        return new AuthorResponse(user.getId(), user.getName(), user.getUsername(), user.getAvatarUrl());
    }
}
