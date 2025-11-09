package com.blog.modules.post.infrastructure.adapter.out.persistence.like;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.port.out.LikeRepository;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final SpringDataLikeRepository jpaRepository;

    public LikeRepositoryImpl(SpringDataLikeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsByUserIdAndPostId(UUID userId, UUID postId) {
        return jpaRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public void save(LikeEntity likeEntity) {
        jpaRepository.save(likeEntity);
    }

    @Override
    public void delete(UUID userId, UUID postId) {
        jpaRepository.delete(new LikeEntity(userId, postId));
    }

}
