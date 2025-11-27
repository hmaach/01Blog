package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository jpaRepository;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<User> findAll(Instant before, Pageable pageable) {

        Page<UserEntity> users;

        if (before == null && pageable != null) {
            users = jpaRepository.findAll(pageable);
        } else {
            users = jpaRepository.findAllBefore(before, pageable);
        }

        return users
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findUserWithAvatar(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public String getUserReadme(UUID userId) {
        return jpaRepository.findReadmeById(userId);
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
        return jpaRepository.findAvatarMediaIdById(userId);
    }

    @Override
    public void updateAvatarId(UUID userId, UUID avatarId) {
        jpaRepository.updateAvatarId(userId, avatarId);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(jpaRepository.save(UserMapper.toEntity(user)));
    }

    @Override
    public void ban(UUID userId) {
        jpaRepository.ban(userId);
    }

    @Override
    public void unban(UUID userId) {
        jpaRepository.unban(userId);
    }

    @Override
    public void incrementImpressionsCount(UUID userId) {
        jpaRepository.incrementImpressionsCount(userId);
    }

    @Override
    public void incrementSubscriptionsCount(UUID userId) {
        jpaRepository.incrementSubscriptionsCount(userId);
    }

    @Override
    public void decrementSubscriptionsCount(UUID userId) {
        jpaRepository.decrementSubscriptionsCount(userId);
    }

    @Override
    public void incrementPostsCount(UUID userId) {
        jpaRepository.incrementPostsCount(userId);
    }

    @Override
    public void decrementPostsCount(UUID userId) {
        jpaRepository.decrementPostsCount(userId);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

}
