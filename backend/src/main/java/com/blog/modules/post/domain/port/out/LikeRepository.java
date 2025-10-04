package com.blog.modules.post.domain.port.out;

import java.util.UUID;

import com.blog.modules.post.infrastructure.adapter.out.persistence.like.LikeEntity;

public interface LikeRepository {

    boolean existsByUserIdAndPostId(UUID userId, UUID postId);

    void save(LikeEntity likeEntity);

    void delete(UUID userId, UUID postId);
}
