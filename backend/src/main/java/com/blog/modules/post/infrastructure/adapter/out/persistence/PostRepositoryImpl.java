package com.blog.modules.post.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @Override
    public Post save(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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
    public List<Post> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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

    // @Override
    // public List<Post> findAll() {
    //     return mongoTemplate.findAll(Post.class);
    // }

    // @Override
    // public void deleteById(String id) {
    //     Query query = new Query();
    //     query.addCriteria(Criteria.where("id").is(id));
    //     mongoTemplate.remove(query, Post.class);
    // }
}
