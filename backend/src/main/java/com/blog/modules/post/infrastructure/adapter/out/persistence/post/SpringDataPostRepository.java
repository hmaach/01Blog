package com.blog.modules.post.infrastructure.adapter.out.persistence.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostRepository extends JpaRepository<PostEntity, UUID> {

    List<PostEntity> findByUserId(UUID userId);
}
