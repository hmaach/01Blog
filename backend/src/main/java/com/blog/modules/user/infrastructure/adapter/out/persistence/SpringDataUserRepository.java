package com.blog.modules.user.infrastructure.adapter.out.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.readme FROM UserEntity u WHERE u.id = :userId")
    String findReadmeById(@Param("userId") UUID userId);
}
