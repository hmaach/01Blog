package com.blog.modules.post.domain.port.out;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Comment;

public interface CommentRepository {

    List<Comment> findAll(Pageable pageable);
    
}
