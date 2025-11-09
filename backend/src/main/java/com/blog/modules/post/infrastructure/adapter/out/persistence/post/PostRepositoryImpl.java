package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia.PostMediaEntity;
import com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia.SpringDataPostMediaRepository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SpringDataPostRepository jpaRepository;
    private final SpringDataPostMediaRepository postMediaJpaRepository;

    public PostRepositoryImpl(SpringDataPostRepository jpaRepository,
            SpringDataPostMediaRepository postMediaJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.postMediaJpaRepository = postMediaJpaRepository;
    }

    @Override
    public List<Post> findAll(Instant before, Pageable pageable) {

        if (before == null) {
            return jpaRepository.findAll(pageable)
                    .stream()
                    .map(PostMapper::toDomain)
                    .toList();
        } else {
            return jpaRepository.findPostsBefore(before, pageable)
                    .stream()
                    .map(PostMapper::toDomain)
                    .toList();
        }
    }

    @Override
    public List<Post> findByUserId(UUID userId, Pageable pageable) {
        return jpaRepository.findByUserId(userId, pageable)
                .stream()
                .map(PostMapper::toDomain)
                .toList();
    }

    @Override
    public List<Post> findByUserUsername(String username, Pageable pageable) {
        return jpaRepository.findByUserUsername(username, pageable)
                .stream()
                .map(PostMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Post> findById(UUID postId) {
        return jpaRepository.findById(postId).map(PostMapper::toDomain);
    }

    @Override
    public Boolean existsById(UUID postId) {
        return jpaRepository.existsById(postId);
    }

    @Override
    public Post save(Post post) {
        PostEntity entity = PostMapper.toEntity(post);
        return PostMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void attachMediaToPost(UUID postId, UUID mediaId) {
        PostMediaEntity entity = new PostMediaEntity(postId, mediaId, Instant.now());
        postMediaJpaRepository.save(entity);
    }

    @Override
    public void incrementLikesCount(UUID postId) {
        jpaRepository.incrementLikesCount(postId);
    }

    @Override
    public void decrementLikesCount(UUID postId) {
        jpaRepository.decrementLikesCount(postId);
    }

    @Override
    public void incrementImpressionsCount(List<UUID> postIds) {
        jpaRepository.incrementImpressionsCount(postIds);
    }

    @Override
    public void hidePostById(UUID postId) {
        jpaRepository.hidePostById(postId);
    }

    @Override
    public void deleteById(UUID postId) {
        jpaRepository.deleteById(postId);
    }

}
