package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("""
            SELECT u
            FROM UserEntity u
            WHERE u.createdAt < :before
            ORDER BY u.createdAt DESC
        """)
    Page<UserEntity> findAllBefore(@Param("before") Instant before, Pageable pageable);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.avatar WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.avatar WHERE u.id = :userId")
    Optional<UserEntity> findUserWithAvatar(UUID userId);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.avatar WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("SELECT u.avatar.id FROM UserEntity u WHERE u.id = :userId")
    Optional<UUID> findAvatarMediaIdById(@Param("userId") UUID userId);

    @Query("SELECT u.readme FROM UserEntity u WHERE u.id = :userId")
    String findReadmeById(@Param("userId") UUID userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'banned' WHERE u.id = :id")
    void ban(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'active' WHERE u.id = :id")
    void unban(@Param("id") UUID id);

    // @Modifying
    // @Query("UPDATE UserEntity u SET u.avatarMediaId = :avatarId WHERE u.id = :userId")
    // void updateAvatarId(@Param("userId") UUID userId, @Param("avatarId") UUID avatarId);
    @Modifying
    @Query("UPDATE UserEntity u SET u.avatar.id = :avatarId WHERE u.id = :userId")
    void updateAvatarId(@Param("userId") UUID userId, @Param("avatarId") UUID avatarId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.impressionsCount = u.impressionsCount + 1 WHERE u.id = :userId")
    void incrementImpressionsCount(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.subscribersCount = u.subscribersCount + 1 WHERE u.id = :userId")
    void incrementSubscriptionsCount(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.subscribersCount = u.subscribersCount - 1 WHERE u.id = :userId")
    void decrementSubscriptionsCount(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.postsCount = u.postsCount + 1 WHERE u.id = :userId")
    void incrementPostsCount(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.postsCount = u.postsCount - 1 WHERE u.id = :userId")
    void decrementPostsCount(@Param("userId") UUID userId);

}
