package com.blog.modules.user.dto;

import java.time.LocalDateTime;

import com.blog.domain.model.User;

public record UserResponse(
        String id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
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
