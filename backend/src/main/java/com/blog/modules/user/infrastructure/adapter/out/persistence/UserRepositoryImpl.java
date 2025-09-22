package com.blog.modules.user.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

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
    public Optional<User> findById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);
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
    public void deleteById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public void ban(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ban'");
    }

    public void unban(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unban'");
    }
}
