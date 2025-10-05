package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.domain.model.Comment;

public record CommentResponse(
        UUID id,
        UUID userId,
        String text,
        Instant createdAt
        ) {

    // TODO: create a user response special for the comments and posts conatin (name, username, avatar) 
    public static CommentResponse fromDomain(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUserId(),
                comment.getText(),
                comment.getCreatedAt()
        );
    }
}
