package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;

public record LoginResponse(
        String token,
        Instant expiresAt
        ) {

    public static LoginResponse fromDomain(String token, Instant expiresAt) {
        return new LoginResponse(
                token,
                expiresAt
        );
    }
}
