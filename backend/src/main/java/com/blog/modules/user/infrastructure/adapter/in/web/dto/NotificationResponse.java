package com.blog.modules.user.infrastructure.adapter.in.web.dto;

import java.util.UUID;

import com.blog.modules.post.infrastructure.adapter.in.web.dto.AuthorResponse;
import com.blog.modules.user.domain.model.Notification;

public record NotificationResponse(
        UUID id,
        AuthorResponse author,
        UUID postId,
        Boolean seen
        ) {

    public static NotificationResponse fromDomain(Notification n) {
        return new NotificationResponse(
                n.getId(),
                AuthorResponse.fromDomain(n.getUser()),
                n.getPostId(),
                n.getSeen()
        );
    }
}
