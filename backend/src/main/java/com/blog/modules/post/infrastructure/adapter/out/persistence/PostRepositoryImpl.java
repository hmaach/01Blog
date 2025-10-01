package com.blog.modules.post.infrastructure.adapter.out.persistence;

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
    public Post save(Post post) {
        PostEntity entity = PostMapper.toEntity(post);
        entity = entityManager.merge(entity);
        return PostMapper.toDomain(entity);
    }

    @Override
    public Optional<Post> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Post> findByUserId(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

    @Override
    @Transactional
    public void attachMediaToPost(UUID postId, UUID mediaId) {
        PostMediaEntity entity = new PostMediaEntity();
        entity.setPostId(postId);
        entity.setMediaId(mediaId);
        entity.setCreatedAt(Instant.now());

        entityManager.persist(entity);
    }

    @Override
    public void deleteById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
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
    @Override
    public List<Post> findAll() {
        TypedQuery<PostEntity> query = entityManager.createQuery("SELECT u FROM PostEntity u", PostEntity.class);
        return query.getResultList().stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
    }

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
