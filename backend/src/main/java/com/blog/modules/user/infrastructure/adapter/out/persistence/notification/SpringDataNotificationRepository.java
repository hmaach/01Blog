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
            WHERE n.userId = :userId
                """)
    Page<NotificationEntity> findAll(@Param("userId") UUID userId, Pageable pageable);

    @Query("""
            SELECT n
            FROM NotificationEntity n
            WHERE n.createdAt < :before
            AND n.userId = :userId
                """)
    Page<NotificationEntity> findAllBefore(@Param("userId") UUID userId, Instant before, Pageable pageable);

    @Modifying
    @Query(value = """
            INSERT INTO notifications(id, user_id, post_owner_id, post_id, created_at)
            SELECT gen_random_uuid(), s.subscriber_id, :userId, :postId, :now
            FROM subscriptions s
            WHERE s.subscribed_to_id = :userId
            ON CONFLICT (id) DO NOTHING;
            """, nativeQuery = true)
    void createNotifications(@Param("userId") UUID userId, @Param("postId") UUID postId, @Param("now") Instant now);

    @Modifying
    @Query("UPDATE NotificationEntity n SET n.seen = true WHERE n.id = :notifId AND n.userId = :userId")
    void markNotifSeen(@Param("userId") UUID userId, @Param("notifId") UUID notifId);

}
