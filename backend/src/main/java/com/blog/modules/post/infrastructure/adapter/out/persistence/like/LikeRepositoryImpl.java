package com.blog.modules.post.infrastructure.adapter.out.persistence.like;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.port.out.LikeRepository;

import jakarta.persistence.EntityManager;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final EntityManager entityManager;

    public LikeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer likePost(UUID postId, UUID userId) {
        entityManager.createNativeQuery("""
        WITH upsert AS (
            INSERT INTO likes (user_id, post_id, created_at)
            VALUES (:userId, :postId, NOW())
            ON CONFLICT (user_id, post_id) DO NOTHING
            RETURNING *
        )
        DELETE FROM likes
        WHERE user_id = :userId AND post_id = :postId
        AND NOT EXISTS (SELECT 1 FROM upsert)
    """)
                .setParameter("userId", userId)
                .setParameter("postId", postId)
                .executeUpdate();

        Long count = (Long) entityManager.createQuery(
                "SELECT COUNT(l) FROM LikeEntity l WHERE l.id.postId = :postId")
                .setParameter("postId", postId)
                .getSingleResult();

        return count.intValue();
    }

}
