package com.blog.modules.user.infrastructure.adapter.out.persistence.notification;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    @Query("""
        SELECT n
        FROM NotificationEntity n
        WHERE n.createdAt < :before
            """)
    Page<NotificationEntity> findAllBefore(Instant before, Pageable pageable);

    @Modifying
    @Query(value = """
        INSERT INTO notifications(user_id, post_id, created_at)
        SELECT f.follower_id, :postId, CURRENT_TIMESTAMP
        FROM followers f
        WHERE f.followee_id = :userId
        """, nativeQuery = true)
    void createNotifications(@Param("userId") UUID userId, @Param("userId") UUID postId);

}
