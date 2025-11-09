package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataMediaRepository extends JpaRepository<MediaEntity, UUID> {

    @Query("SELECT m FROM MediaEntity m JOIN PostMediaEntity pm ON m.id = pm.id.mediaId WHERE pm.id.postId = :postId")
    List<MediaEntity> findByPostId(UUID postId);
}
