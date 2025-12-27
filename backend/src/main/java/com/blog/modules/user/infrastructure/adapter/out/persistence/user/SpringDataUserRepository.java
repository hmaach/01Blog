package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.time.Instant;
import java.util.List;
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
                WHERE (:query IS NULL OR :query = '' OR
                    LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) OR
                    LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))
                    )
            """)
    Page<UserEntity> findAll(@Param("query") String query, Pageable pageable);

    @Query("""
                SELECT u
                FROM UserEntity u
                WHERE u.createdAt < :before
                AND (:query IS NULL OR :query = '' OR
                    LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) OR
                    LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))
                    )
            """)
    Page<UserEntity> findAllBefore(@Param("query") String query, @Param("before") Instant before, Pageable pageable);

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

    @Query("SELECT u.uploadsSize FROM UserEntity u WHERE u.id = :userId")
    long getUploadsSize(UUID userId);

    @Query("SELECT u.emailVerified FROM UserEntity u WHERE u.id = :userId")
    boolean isEmailVerified(UUID userId);

    @Query("SELECT u.role = 'ADMIN' FROM UserEntity u WHERE u.id = :userId")
    boolean isAdmin(@Param("userId") UUID userId);

    @Query("SELECT u.status = 'BANNED' FROM UserEntity u WHERE u.id = :userId")
    boolean isBanned(UUID userId);

    @Query("SELECT u.id FROM UserEntity u WHERE u.role = 'ADMIN'")
    List<UUID> findFirstAdmin(@Param("userId") UUID userId, Pageable pageable);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'banned' WHERE u.id = :id")
    void ban(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'active' WHERE u.id = :id")
    void unban(@Param("id") UUID id);

    // @Modifying
    // @Query("UPDATE UserEntity u SET u.avatarMediaId = :avatarId WHERE u.id =
    // :userId")
    // void updateAvatarId(@Param("userId") UUID userId, @Param("avatarId") UUID
    // avatarId);
    @Modifying
    @Query("UPDATE UserEntity u SET u.avatar.id = :avatarId WHERE u.id = :userId")
    void updateAvatarId(@Param("userId") UUID userId, @Param("avatarId") UUID avatarId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.uploadsSize = u.uploadsSize + :size WHERE u.id = :userId")
    void incrementUploadsSize(@Param("userId") UUID userId, @Param("size") long size);

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

    @Override
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :userId", nativeQuery = true)
    void deleteById(@Param("userId") UUID userId);

}
