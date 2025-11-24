package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.domain.model.Comment;

public record CommentResponse(
        UUID id,
        UUID postId,
        AuthorResponse author,
        boolean isOwner,
        String content,
        Instant createdAt
        ) {

    public static CommentResponse fromDomain(Comment comment, boolean isOwner) {
        AuthorResponse author = comment.getUser() != null ? AuthorResponse.fromDomain(comment.getUser()) : null;

        return new CommentResponse(
                comment.getId(),
                comment.getPostId(),
                author,
                isOwner,
                comment.getText(),
                comment.getCreatedAt()
        );
    }
}
