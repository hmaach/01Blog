package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
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
        Page<PostEntity> posts;

        if (before == null && pageable != null) {
            posts = jpaRepository.findAllPosts(pageable);
        } else {
            posts = jpaRepository.findAllPostsBefore(before, pageable);
        }

        posts.forEach(post
                -> {

            Optional<PostMediaEntity> pm = postMediaJpaRepository.findFirstByIdPostIdOrderByCreatedAtAsc(post.getId());
            if (!pm.isEmpty()) {
                post.setFirstMedia(pm.get().getMediaEntity());
            }
        }
        );

        return posts.stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findFeedPosts(UUID currUserId, Instant before, Pageable pageable) {

        List<PostEntity> posts;

        if (before == null) {
            posts = jpaRepository.findFeedPosts(currUserId, pageable);
        } else {
            posts = jpaRepository.findFeedPostsBefore(currUserId, before, pageable);
        }

        posts.forEach(post
                -> {

            Optional<PostMediaEntity> pm = postMediaJpaRepository.findFirstByIdPostIdOrderByCreatedAtAsc(post.getId());
            if (!pm.isEmpty()) {
                post.setFirstMedia(pm.get().getMediaEntity());
            }
        }
        );

        return posts.stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
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
        if (postId == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(postId).map(PostMapper::toDomain);
    }

    @Override
    public Boolean existsById(UUID postId) {
        if (postId != null) {
            return jpaRepository.existsById(postId);
        }
        return false;
    }

    @Override
    public Post save(Post post) {
        PostEntity entity = PostMapper.toEntity(post);
        if (entity != null) {
            return PostMapper.toDomain(jpaRepository.save(entity));
        }
        return null;
    }

    @Override
    public void changeStatus(UUID postId, String status) {
        jpaRepository.changeStatus(postId, status);
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
    public void incrementCommentsCount(UUID postId) {
        jpaRepository.incrementCommentsCount(postId);
    }

    @Override
    public void decrementCommentsCount(UUID postId) {
        jpaRepository.decrementCommentsCount(postId);
    }

    @Override
    public void incrementImpressionsCount(UUID postId) {
        jpaRepository.incrementImpressionsCount(postId);
    }

    @Override
    public void hidePostById(UUID postId) {
        jpaRepository.hidePostById(postId);
    }

    @Override
    public void deleteById(UUID postId) {
        if (postId != null) {
            jpaRepository.deleteById(postId);
        }
    }
}
