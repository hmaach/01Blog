package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;

import jakarta.persistence.EntityManager;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SpringDataPostRepository jpaRepository;
    private final EntityManager entityManager;

    public PostRepositoryImpl(SpringDataPostRepository jpaRepository, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.entityManager = entityManager;
    }

    // --- CRUD operations ---
    @Override
    public List<Post> findAll() {
        return jpaRepository.findAll().stream()
                .map(PostMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Post> findById(UUID id) {
        return jpaRepository.findById(id).map(PostMapper::toDomain);
    }

    @Override
    public List<Post> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(PostMapper::toDomain)
                .toList();
    }

    @Override
    public Post save(Post post) {
        PostEntity entity = PostMapper.toEntity(post);
        return PostMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    // --- Custom operations ---
    @Override
    public void attachMediaToPost(UUID postId, UUID mediaId) {
        PostMediaEntity entity = new PostMediaEntity(postId, mediaId, Instant.now());
        entityManager.persist(entity);
    }

    @Override
    public void incrementLikesCount(UUID postId) {
        entityManager.createQuery("""
            UPDATE PostEntity p
            SET p.likesCount = p.likesCount + 1
            WHERE p.id = :postId
        """)
                .setParameter("postId", postId)
                .executeUpdate();
    }

    @Override
    public void decrementLikesCount(UUID postId) {
        entityManager.createQuery("""
            UPDATE PostEntity p
            SET p.likesCount = p.likesCount - 1
            WHERE p.id = :postId
        """)
                .setParameter("postId", postId)
                .executeUpdate();
    }

    @Override
    public void incrementImpressionsCount(List<UUID> postIds) {
        entityManager.createQuery("""
        UPDATE PostEntity p
        SET p.impressionsCount = p.impressionsCount + 1
        WHERE p.id IN :postIds
    """)
                .setParameter("postIds", postIds)
                .executeUpdate();
    }

    @Override
    public void deletePostMediaLinks(UUID postId) {
        entityManager.createQuery("""
        DELETE FROM PostMediaEntity pm
        WHERE pm.id.postId = :postId
    """)
                .setParameter("postId", postId)
                .executeUpdate();
    }

    @Override
    public void deleteMediaLinks(UUID mediaId) {
        entityManager.createQuery("""
                    DELETE FROM PostMediaEntity pm
                    WHERE pm.id.mediaId = :mediaId
                """)
                .setParameter("mediaId", mediaId)
                .executeUpdate();
    }

    @Override
    public void hidePostById(UUID postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hidePostById'");
    }
}
