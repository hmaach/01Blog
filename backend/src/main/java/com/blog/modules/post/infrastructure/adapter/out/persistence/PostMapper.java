package com.blog.modules.post.infrastructure.adapter.out.persistence;

import com.blog.modules.post.domain.model.Post;

public class PostMapper {

    public static PostEntity toEntity(Post post) {
        if (post == null) {
            return null;
        }

        return new PostEntity(
                post.getId(),
                post.getName(),
                post.getDescription(),
                post.getPrice(),
                post.getUserId(),
                post.getCreatedAt()
        );
    }

    public static Post toDomain(PostEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Post(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getUserId(),
                entity.getCreatedAt()
        );
    }
}
