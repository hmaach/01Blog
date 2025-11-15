package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserMapper;

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
        User user = entity.getUser() != null ? UserMapper.toDomain(entity.getUser()) : null;
        return new Comment(
                entity.getId(),
                user,
                entity.getUserId(),
                entity.getPostId(),
                entity.getText(),
                entity.getCreatedAt()
        );
    }
}
