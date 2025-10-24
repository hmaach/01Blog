package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import com.blog.modules.user.domain.model.User;

public record AuthorResponse(
        String name,
        String username,
        String avatarUrl
        ) {

    public static AuthorResponse fromDomain(User user, String avatarUrl) {
        return new AuthorResponse(user.getName(), user.getUsername(), avatarUrl);
    }
}
