package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String role,
        Instant createdAt
        ) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
