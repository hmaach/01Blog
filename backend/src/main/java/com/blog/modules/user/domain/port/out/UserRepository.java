package com.blog.modules.user.domain.port.out;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.user.domain.model.User;

public interface UserRepository {

    List<User> findAll(String query, Instant before, Pageable size);

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    String getUserReadme(UUID userId);

    boolean existsById(UUID userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean isEmailVerified(UUID userId);

    boolean isBanned(UUID userId);

    boolean isAdmin(UUID userId);

    boolean isSuperAdmin(UUID userId);

    Optional<UUID> getAvatarId(UUID userId);

    long getUploadsSize(UUID currentUserId);

    void updateAvatarId(UUID userId, UUID avatarUrl);

    void ban(UUID userId);

    void unban(UUID userId);

    void incrementImpressionsCount(UUID userId);

    void incrementSubscriptionsCount(UUID userId);

    void decrementSubscriptionsCount(UUID userId);

    void incrementPostsCount(UUID userId);

    void decrementPostsCount(UUID userId);

    void deleteById(UUID id);

    void incrementUploadsSize(UUID currentUserId, long size);

}
