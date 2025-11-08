package com.blog.modules.post.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.out.CommentRepository;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreateCommentCommand;
import com.blog.modules.post.infrastructure.exception.CommentNotFoundException;
import com.blog.shared.infrastructure.exception.ForbiddenException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository
    ) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getByPostId(UUID postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    public Comment createComment(UUID postId, UUID currUserId, CreateCommentCommand cmd) {
        UUID commentId = UUID.randomUUID();

        Comment comment = new Comment(
                commentId,
                currUserId,
                postId,
                cmd.comment(),
                Instant.now()
        );

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId, UUID currUserId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId.toString()));

        if (!isAdmin && !currUserId.equals(comment.getUserId())) {
            throw new ForbiddenException();
        }

        commentRepository.deleteById(commentId);
    }
}
