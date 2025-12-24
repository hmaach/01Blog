package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            AND (
                p.user.id IN (
                    SELECT s.id.subscribedToId
                    FROM SubscriptionEntity s
                    WHERE s.id.subscriberId = :currUserId
                )
                OR p.user.id = :currUserId
            )
            ORDER BY p.createdAt DESC
        """)
    List<PostEntity> findFeedPosts(@Param("currUserId") UUID currUserId, Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            AND p.createdAt < :before
            AND (
                p.user.id IN (
                    SELECT s.id.subscribedToId
                    FROM SubscriptionEntity s
                    WHERE s.id.subscriberId = :currUserId
                )
                OR p.user.id = :currUserId
            )
            ORDER BY p.createdAt DESC
        """)
    List<PostEntity> findFeedPostsBefore(@Param("currUserId") UUID currUserId, @Param("before") Instant before, Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            ORDER BY p.createdAt DESC
        """)
    Page<PostEntity> findAllPosts(Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            AND p.createdAt < :before
            ORDER BY p.createdAt DESC
        """)
    Page<PostEntity> findAllPostsBefore(Instant before, Pageable pageable);

    Page<PostEntity> findByUserId(UUID userId, Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            AND p.user.username = :username
            ORDER BY p.createdAt DESC
        """)
    Page<PostEntity> findByUserUsername(@Param("username") String username, Pageable pageable);

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE p.status = 'PUBLISHED'
            AND p.user.username = :username
            AND p.createdAt < :before
            ORDER BY p.createdAt DESC
        """)
    Page<PostEntity> findByUserUsernameBefore(String username, Instant before, Pageable pageable);


    @Query("SELECT p FROM PostEntity p WHERE p.createdAt < :before ORDER BY p.createdAt DESC")
    List<PostEntity> findPostsBefore(@Param("before") Instant before, Pageable pageable);

    @Query(value = """
        SELECT m.url
        FROM post_media pm
        JOIN media m ON m.id = pm.media_id
        WHERE pm.post_id = :id
        ORDER BY pm.created_at ASC
        LIMIT 1
    """, nativeQuery = true)
    String findFirstmediaUrl(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE PostEntity p SET p.status = :status WHERE p.id = :postId")
    void changeStatus(@Param("postId") UUID postId, @Param("status") String status);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount + 1 WHERE p.id = :postId")
    void incrementLikesCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    void decrementLikesCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.commentsCount = p.commentsCount + 1 WHERE p.id = :postId")
    void incrementCommentsCount(UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.commentsCount = p.commentsCount - 1 WHERE p.id = :postId")
    void decrementCommentsCount(UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.impressionsCount = p.impressionsCount + 1 WHERE p.id = :postId")
    void incrementImpressionsCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.status = 'hidden' WHERE p.id = :id")
    void hidePostById(@Param("postId") UUID postId);


}
