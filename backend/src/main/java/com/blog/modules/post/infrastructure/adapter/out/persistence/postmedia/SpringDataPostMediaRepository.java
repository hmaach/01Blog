package com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostMediaRepository extends JpaRepository<PostMediaEntity, UUID> {

    // @Modifying
    // @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    // void deletePostMediaLinks(@Param("postId") UUID postId);

    // @Modifying
    // @Query("UPDATE PostEntity p SET p.likesCount = p.likesCount - 1 WHERE p.id = :postId")
    // void deleteMediaLinks(@Param("postId") UUID mediaId);

}
