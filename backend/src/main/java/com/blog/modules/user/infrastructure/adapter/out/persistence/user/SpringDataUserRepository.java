package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UUID> findAvatarMediaIdById(UUID userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.readme FROM UserEntity u WHERE u.id = :userId")
    String findReadmeById(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'banned' WHERE u.id = :id")
    void ban(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'active' WHERE u.id = :id")
    void unban(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.avatarMediaId = :avatarId WHERE u.id = :userId")
    void updateAvatarId(@Param("userId") UUID userId, @Param("avatarId") UUID avatarId);

}
