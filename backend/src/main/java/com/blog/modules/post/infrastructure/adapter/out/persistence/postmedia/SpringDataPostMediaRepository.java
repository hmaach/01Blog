package com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostMediaRepository extends JpaRepository<PostMediaEntity, UUID> {

    // @Query("""
    //     SELECT pm 
    //     FROM PostMediaEntity pm
    //     WHERE pm.post_id = :postId
    //     LIMIT 1
    // """)
    // Optional<PostMediaEntity> findFirstByPostId(@Param("postId") UUID postId);

    Optional<PostMediaEntity> findFirstByIdPostIdOrderByCreatedAtAsc(UUID postId);


    // @Modifying
    // @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    // void deletePostMediaLinks(@Param("postId") UUID postId);
    // @Modifying
    // @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    // void deleteMediaLinks(@Param("postId") UUID mediaId);
}
