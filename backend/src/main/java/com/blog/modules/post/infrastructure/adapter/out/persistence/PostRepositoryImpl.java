package com.blog.modules.post.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final MongoTemplate mongoTemplate;

    public PostRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Post save(Post post) {
        return mongoTemplate.save(post);
    }

    @Override
    public Optional<Post> findById(String id) {
        Post post = mongoTemplate.findById(id, Post.class);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Post.class);
    }

    @Override
    public List<Post> findAll() {
        return mongoTemplate.findAll(Post.class);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Post.class);
    }
}
