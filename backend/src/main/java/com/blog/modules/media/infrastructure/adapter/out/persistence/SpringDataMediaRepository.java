package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataMediaRepository extends JpaRepository<MediaEntity, UUID> {

    @Query("SELECT m FROM MediaEntity m JOIN PostMediaEntity pm ON m.id = pm.id.mediaId WHERE pm.id.postId = :postId")
    List<MediaEntity> findByPostId(UUID postId);

    // @Query(value = """
    //     SELECT m
    //     FROM PostMediaEntity pm
    //     JOIN MediaEntity m ON m.id = pm.media_id
    //     WHERE pm.post_id = :post_id
    // """)
    // MediaEntity findFirstmediaUrl(@Param("post_id") UUID post_id, Pageable pageable);
}
