package com.blog.modules.post.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.event.PostCreatedCommentEvent;
import com.blog.modules.post.domain.event.PostDeletedCommentEvent;
import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.out.CommentRepository;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreateCommentCommand;
import com.blog.modules.post.infrastructure.exception.CommentNotFoundException;
import com.blog.shared.infrastructure.exception.ForbiddenException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.commentRepository = commentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Comment> getComments(UUID postId, Instant before, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);
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
        Comment newComment = commentRepository.save(comment);
        eventPublisher.publishEvent(new PostCreatedCommentEvent(postId));
        return newComment;
    }

    @Override
    public void deleteComment(UUID commentId, UUID currUserId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId.toString()));

        if (!isAdmin && !currUserId.equals(comment.getUserId())) {
            throw new ForbiddenException();
        }

        commentRepository.deleteById(commentId);
        eventPublisher.publishEvent(new PostDeletedCommentEvent(comment.getPostId()));
    }
}
