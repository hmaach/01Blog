package com.blog.modules.post.infrastructure.adapter.out.persistence.like;

import com.blog.modules.post.domain.model.Like;

public class LikeMapper {

    public static LikeEntity toEntity(Like like) {
        LikeEntity entity = new LikeEntity();
        entity.setId(new LikeEntity.LikeKey(like.getUserId(), like.getPostId()));
        entity.setCreatedAt(like.getCreatedAt());
        return entity;
    }

    public static Like toDomain(LikeEntity entity) {
        return new Like(
                entity.getId().getUserId(),
                entity.getId().getPostId(),
                entity.getCreatedAt()
        );
    }
}
