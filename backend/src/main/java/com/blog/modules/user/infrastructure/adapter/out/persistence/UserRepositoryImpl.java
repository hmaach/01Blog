package com.blog.modules.user.infrastructure.adapter.out.persistence;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return UserMapper.toDomain(entity);
    }

    @Override
    public Optional<User> findById(String id) {
        UserEntity entity = entityManager.find(UserEntity.class, UUID.fromString(id));
        return Optional.ofNullable(UserMapper.toDomain(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class
        );
        query.setParameter("email", email);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(UserMapper.toDomain(result.get(0)));
    }

    @Override
    public List<User> findAll() {
        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class);
        return query.getResultList().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        UserEntity entity = entityManager.find(UserEntity.class, UUID.fromString(id));
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
