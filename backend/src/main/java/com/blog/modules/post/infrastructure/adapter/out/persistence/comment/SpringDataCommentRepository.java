package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataCommentRepository extends JpaRepository<CommentEntity, UUID> {

    Page<CommentEntity> findByPostId(UUID postId, Pageable pageable);

    @Query("""
                SELECT c
                FROM CommentEntity c
                WHERE c.postId = :postId
                AND c.createdAt < :before
            """)
    Page<CommentEntity> findByPostIdBefore(@Param("postId") UUID postId, @Param("before") Instant before,
            Pageable pageable);

}
