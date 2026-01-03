package com.blog.modules.post.infrastructure.adapter.out.persistence.postmedia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPostMediaRepository extends JpaRepository<PostMediaEntity, UUID> {

    Optional<PostMediaEntity> findFirstByIdPostIdOrderByCreatedAtAsc(UUID postId);

    @Modifying
    @Query("DELETE FROM PostMediaEntity pm WHERE pm.id.postId = :postId AND pm.id.mediaId = :mediaId")
    void deletePostMediaLink(@Param("postId") UUID postId, @Param("mediaId") UUID mediaId);

}
