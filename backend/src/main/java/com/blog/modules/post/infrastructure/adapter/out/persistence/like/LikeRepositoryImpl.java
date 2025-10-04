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
    public boolean existsByUserIdAndPostId(UUID userId, UUID postId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(l) FROM LikeEntity l WHERE l.id.userId = :userId AND l.id.postId = :postId", Long.class)
                .setParameter("userId", userId)
                .setParameter("postId", postId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public void save(LikeEntity likeEntity) {
        entityManager.persist(likeEntity);
    }

    @Override
    public void delete(UUID userId, UUID postId) {
        entityManager.createQuery("""
        DELETE FROM LikeEntity l
        WHERE l.id.userId = :userId AND l.id.postId = :postId
    """)
                .setParameter("userId", userId)
                .setParameter("postId", postId)
                .executeUpdate();
    }

}
