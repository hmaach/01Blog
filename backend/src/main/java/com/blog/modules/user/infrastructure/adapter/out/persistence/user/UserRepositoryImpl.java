package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.blog.modules.user.domain.model.Notification;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.out.UserRepository;
import com.blog.modules.user.infrastructure.adapter.out.persistence.notification.NotificationEntity;
import com.blog.modules.user.infrastructure.adapter.out.persistence.notification.NotificationMapper;
import com.blog.modules.user.infrastructure.adapter.out.persistence.notification.SpringDataNotificationRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository jpaRepository;
    private final SpringDataNotificationRepository noticicationRepository;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository,
            SpringDataNotificationRepository noticicationRepository) {
        this.jpaRepository = jpaRepository;
        this.noticicationRepository = noticicationRepository;
    }

    @Override
    public List<User> findAll(String query, Instant before, Pageable pageable) {

        String q = (query == null || query.isBlank()) ? null : query.trim();

        Page<UserEntity> users;

        if (before == null && pageable != null) {
            users = jpaRepository.findAll(q, pageable);
        } else {
            users = jpaRepository.findAllBefore(q, before, pageable);
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
        if (userId == null) {
            return false;
        }
        return jpaRepository.existsById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean isEmailVerified(UUID userId) {
        return jpaRepository.isEmailVerified(userId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean isBanned(UUID userId) {
        return jpaRepository.isBanned(userId);
    }

    @Override
    public boolean isAdmin(UUID userId) {
        return jpaRepository.isAdmin(userId);
    }

    @Override
    public boolean isSuperAdmin(UUID userId) {
        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(0, 1, sort);

        List<UUID> firstAdminId = jpaRepository.findFirstAdmin(userId, pageable);

        if (!firstAdminId.isEmpty()) {
            return userId.equals(firstAdminId.get(0));
        }
        return false;
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
        UserEntity entity = UserMapper.toEntity(user);
        if (entity != null) {
            return UserMapper.toDomain(jpaRepository.save(entity));
        }
        return null;
    }

    @Override
    public long getUploadsSize(UUID userId) {
        return jpaRepository.getUploadsSize(userId);
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
    public void incrementUploadsSize(UUID userId, long size) {
        jpaRepository.incrementUploadsSize(userId, size);
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
    public void deleteById(UUID userId) {
        if (userId != null) {
            jpaRepository.deleteById(userId);
        }
    }

    @Override
    public List<Notification> getNotifications(UUID userId, Instant before, Pageable pageable) {
        Page<NotificationEntity> notification;

        if (before == null && pageable != null) {
            notification = noticicationRepository.findAll(userId, pageable);
        } else {
            notification = noticicationRepository.findAllBefore(userId, before, pageable);
        }

        return notification
                .stream()
                .map(NotificationMapper::toDomain)
                .toList();
    }

    @Override
    public void createNotifications(UUID userId, UUID postId) {
        noticicationRepository.createNotifications(userId, postId, Instant.now());
    }

    @Override
    public void markNotifSeen(UUID userId, UUID notifId) {
        noticicationRepository.markNotifSeen(userId, notifId);
    }

}
