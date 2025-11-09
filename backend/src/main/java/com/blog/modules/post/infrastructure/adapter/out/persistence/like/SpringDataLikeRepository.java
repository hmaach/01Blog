package com.blog.modules.post.infrastructure.adapter.out.persistence.like;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataLikeRepository extends JpaRepository<LikeEntity, UUID> {

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.id.userId = :userId AND l.id.postId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") UUID userId, @Param("postId") UUID postId);
}
