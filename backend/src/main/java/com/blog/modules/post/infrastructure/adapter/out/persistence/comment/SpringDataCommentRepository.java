package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCommentRepository extends JpaRepository<CommentEntity, UUID> {

}
