package com.blog.modules.post.domain.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreateCommentCommand;

public interface CommentService {

    List<Comment> getByPostId(UUID postId, Pageable pageable);

    Comment createComment(UUID postId, UUID currUserId, CreateCommentCommand cmd);

    void deleteComment(UUID commentId, UUID currUserId, boolean isAdmin);

}
