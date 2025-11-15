package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.blog.modules.post.domain.model.Comment;

public record CommentResponse(
        UUID id,
        UUID postId,
        AuthorResponse author,
        String content,
        Instant createdAt
        ) {

    public static CommentResponse fromDomain(Comment comment) {
        AuthorResponse author = comment.getUser() != null ? AuthorResponse.fromDomain(comment.getUser()) : null;

        return new CommentResponse(
                comment.getId(),
                comment.getPostId(),
                author,
                comment.getText(),
                comment.getCreatedAt()
        );
    }
}
