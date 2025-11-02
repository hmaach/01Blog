package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPostRepository extends JpaRepository<PostEntity, UUID> {

    Page<PostEntity> findByUserId(UUID userId, Pageable pageable);

    Page<PostEntity> findByUserUsername(String username, Pageable pageable);

    @Query("SELECT p FROM PostEntity p WHERE p.createdAt < :before ORDER BY p.createdAt DESC")
    List<PostEntity> findPostsBefore(@Param("before") Instant before, Pageable pageable);
}
