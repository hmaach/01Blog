package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.infrastructure.adapter.in.web.dto.AuthorResponse;
import com.blog.modules.user.domain.model.Notification;

public record NotificationResponse(
        UUID id,
        AuthorResponse author,
        UUID postId,
        Boolean seen,
        Instant createdAt) {

    public static NotificationResponse fromDomain(Notification n) {

        AuthorResponse author = n.getUser() != null ? AuthorResponse.fromDomain(n.getUser()) : null;

        return new NotificationResponse(
                n.getId(),
                author,
                n.getPostId(),
                n.getSeen(),
                n.getCreatedAt());
    }
}
