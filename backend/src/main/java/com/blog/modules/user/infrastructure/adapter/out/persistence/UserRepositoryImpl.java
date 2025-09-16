package com.blog.modules.user.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity((User) user);
        UserEntity saved = mongoTemplate.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(String id) {
        UserEntity entity = mongoTemplate.findById(id, UserEntity.class);
        return Optional.ofNullable(UserMapper.toDomain(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        UserEntity entity = mongoTemplate.findOne(query, UserEntity.class);
        return Optional.ofNullable(UserMapper.toDomain(entity));
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(UserEntity.class)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserEntity.class);
    }
}
