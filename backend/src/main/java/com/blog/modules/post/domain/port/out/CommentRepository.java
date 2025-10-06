package com.blog.modules.post.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Comment;

public interface CommentRepository {

    List<Comment> findByPostId(UUID postId, Pageable pageable);

    Optional<Comment> findById(UUID commentId);

    Comment save(Comment comment);

    void deleteById(UUID commentId);
}
