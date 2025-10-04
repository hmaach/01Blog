package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager entityManager;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<PostEntity> query = entityManager.createQuery("SELECT u FROM PostEntity u", PostEntity.class);
        return query.getResultList().stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Post> findById(UUID id) {
        PostEntity entity = entityManager.find(PostEntity.class, id);
        return Optional.ofNullable(PostMapper.toDomain(entity));
    }

    @Override
    public List<Post> findByUserId(UUID userId) {
        TypedQuery<PostEntity> query = entityManager.createQuery(
                "SELECT p FROM PostEntity p WHERE p.userId = :userId", PostEntity.class);
        query.setParameter("userId", userId);

        return query.getResultList().stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Post save(Post post) {
        PostEntity entity = PostMapper.toEntity(post);
        entity = entityManager.merge(entity);
        return PostMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void attachMediaToPost(UUID postId, UUID mediaId) {
        PostMediaEntity entity = new PostMediaEntity(postId, mediaId, Instant.now());
        entityManager.persist(entity);
    }

    // @Override
    // public void incrementLikesCount(UUID postId) {
    //     PostEntity post = entityManager.find(PostEntity.class, postId);
    //     if (post != null) {
    //         post.setLikesCount(post.getLikesCount() + 1);
    //     }
    // }
    // @Override
    // public void decrementLikesCount(UUID postId) {
    //     PostEntity post = entityManager.find(PostEntity.class, postId);
    //     if (post != null) {
    //         post.setLikesCount(post.getLikesCount() - 1);
    //     }
    // }
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
    @Transactional
    public void deletePostMediaLinks(UUID postId) {
        entityManager.createQuery(
                "DELETE FROM PostMediaEntity pm WHERE pm.postId = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }

    @Transactional
    public void deleteMediaLinks(UUID mediaId) {
        entityManager.createQuery(
                "DELETE FROM PostMediaEntity pm WHERE pm.mediaId = :mediaId")
                .setParameter("mediaId", mediaId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        deletePostMediaLinks(id);

        PostEntity entity = entityManager.find(PostEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    // private final MongoTemplate mongoTemplate;
    // public PostRepositoryImpl(MongoTemplate mongoTemplate) {
    //     this.mongoTemplate = mongoTemplate;
    // }
    // @Override
    // public Post save(Post post) {
    //     return mongoTemplate.save(post);
    // }
    // @Override
    // public Optional<Post> findById(String id) {
    //     Post post = mongoTemplate.findById(id, Post.class);
    //     return Optional.ofNullable(post);
    // }
    // @Override
    // public List<Post> findByUserId(String userId) {
    //     Query query = new Query();
    //     query.addCriteria(Criteria.where("userId").is(userId));
    //     return mongoTemplate.find(query, Post.class);
    // }
    @Transactional
    public void hidePostById(UUID postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hidePostById'");
    }

    // @Override
    // public void deleteById(String id) {
    //     Query query = new Query();
    //     query.addCriteria(Criteria.where("id").is(id));
    //     mongoTemplate.remove(query, Post.class);
    // }
}
