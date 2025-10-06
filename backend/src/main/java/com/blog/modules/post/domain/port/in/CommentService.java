package com.blog.modules.post.domain.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Comment;

public interface CommentService {

    List<Comment> getByPostId(UUID postId, Pageable pageable);

}
