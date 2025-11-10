package com.blog.modules.user.infrastructure.adapter.out.persistence.subscription;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataSubscriptionRepository extends JpaRepository<SubscriptionEntity, SubscriptionEntity.SubscriptionKey> {

    @Query("""
    SELECT COUNT(s) > 0
    FROM SubscriptionEntity s
    WHERE s.id.subscriberId = :subscriberId AND s.id.subscribedToId = :targetId
    """)
    boolean isSubscribed(@Param("subscriberId") UUID subscriberId, @Param("targetId") UUID targetId);

    @Modifying
    @Query(
            value = "INSERT INTO subscriptions (subscriber_id, subscribed_to_id) "
            + "VALUES (:currUserId, :targetUserId) "
            + "ON CONFLICT (subscriber_id, subscribed_to_id) DO NOTHING",
            nativeQuery = true
    )
    void executeInsertQuery(@Param("currUserId") UUID currUserId, @Param("targetUserId") UUID targetUserId);

}
