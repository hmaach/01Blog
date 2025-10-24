package com.blog.modules.user.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.blog.modules.post.infrastructure.adapter.out.persistence.post.PostEntity;
import com.blog.modules.post.infrastructure.adapter.out.persistence.post.PostMapper;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;
    private final SpringDataUserRepository jpaRepository;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class);
        return query.getResultList().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(UserMapper.toDomain(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(UserMapper.toDomain(result.get(0)));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class);
        query.setParameter("username", username);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(UserMapper.toDomain(result.get(0)));
    }

    @Override
    public boolean existsById(UUID userId) {
        return jpaRepository.existsById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<UUID> getAvatarId(UUID userId) {

        TypedQuery<UUID> query = entityManager.createQuery(
                "SELECT u.avatarMediaId FROM UserEntity u WHERE u.id = :userId", UUID.class);
        query.setParameter("userId", userId);
        UUID avatarId = query.getSingleResult();
        return Optional.ofNullable(avatarId);
    }

    @Override
    public void updateAvatarId(UUID userId, UUID avatarId) {
        int updated = entityManager.createQuery(
                "UPDATE UserEntity u SET u.avatarMediaId = :avatarId WHERE u.id = :userId")
                .setParameter("avatarId", avatarId)
                .setParameter("userId", userId)
                .executeUpdate();

        if (updated == 0) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        return UserMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void ban(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ban'");
    }

    @Override
    public void unban(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unban'");
    }

    @Override
    public void deleteById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

}
