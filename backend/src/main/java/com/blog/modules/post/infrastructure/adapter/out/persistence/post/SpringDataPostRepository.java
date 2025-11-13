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

    Page<PostEntity> findByUserId(UUID userId, Pageable pageable);

    Page<PostEntity> findByUserUsername(String username, Pageable pageable);

    // @Query("SELECT p FROM PostEntity p "
    //         + "LEFT JOIN FETCH p.postMedias pm "
    //         + "LEFT JOIN FETCH pm.mediaEntity m "
    //         + "WHERE p.status = 'published' "
    //         + "AND (p.user.id IN ("
    //         + "    SELECT s.id.subscribedToId FROM SubscriptionEntity s "
    //         + "    WHERE s.id.subscriberId = :currUserId "
    //         + ") OR p.user.id = :currUserId) "
    //         + "ORDER BY p.createdAt DESC")
    // List<PostEntity> findFeedPosts(@Param("currUserId") UUID currUserId, Pageable pageable);

    // @Query(
    //         "SELECT p FROM PostEntity p "
    //         + "WHERE p.status = 'published' "
    //         + "AND p.createdAt < :before "
    //         + "AND (p.user.id IN ("
    //         + "    SELECT s.id.subscribedToId FROM SubscriptionEntity s "
    //         + "    WHERE s.id.subscriberId = :currUserId"
    //         + ") OR p.user.id = :currUserId) "
    //         + "ORDER BY p.createdAt DESC"
    // )
    // List<PostEntity> findFeedPostsBefore(@Param("currUserId") UUID currUserId, @Param("before") Instant before, Pageable pageable);

    @Query("SELECT p FROM PostEntity p WHERE p.createdAt < :before ORDER BY p.createdAt DESC")
    List<PostEntity> findPostsBefore(@Param("before") Instant before, Pageable pageable);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount + 1 WHERE p.id = :postId")
    void incrementLikesCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    void decrementLikesCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.impressionsCount = p.impressionsCount + 1 WHERE p.id IN :postIds")
    void incrementImpressionsCount(@Param("postId") List<UUID> postIds);

    @Modifying
    @Query("UPDATE PostEntity p SET p.status = 'hidden' WHERE p.id = :id")
    void hidePostById(@Param("postId") UUID postId);

}
