package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserMapper;

public class PostMapper {

    public static PostEntity toEntity(Post post) {
        if (post == null) {
            return null;
        }

        return new PostEntity(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getUserId(),
                post.getStatus(),
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getImpressionsCount(),
                post.getCreatedAt()
        );
    }

    public static Post toDomain(PostEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Post(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getStatus(),
                UserMapper.toDomain(entity.getUser()),
                entity.getFirstMediaUrl(),
                entity.getLikesCount(),
                entity.getCommentsCount(),
                entity.getImpressionsCount(),
                entity.getCreatedAt()
        );
    }
}
