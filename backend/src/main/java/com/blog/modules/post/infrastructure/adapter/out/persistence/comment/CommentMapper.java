package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import com.blog.modules.post.domain.model.Comment;

public class CommentMapper {

    public static CommentEntity toEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setId(comment.getId());
        entity.setUserId(comment.getUserId());
        entity.setPostId(comment.getPostId());
        entity.setText(comment.getText());
        entity.setCreatedAt(comment.getCreatedAt());
        return entity;
    }

    public static Comment toDomain(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getUserId(),
                entity.getPostId(),
                entity.getText(),
                entity.getCreatedAt()
        );
    }
}
