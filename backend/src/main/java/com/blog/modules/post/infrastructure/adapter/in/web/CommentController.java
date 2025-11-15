package com.blog.modules.post.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CommentResponse;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreateCommentCommand;
import com.blog.modules.post.infrastructure.exception.PostNotFoundException;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final JwtService jwtService;

    public CommentController(
            CommentService commentService,
            PostService postService,
            JwtService jwtService
    ) {
        this.commentService = commentService;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @PathVariable UUID postId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Comment> comments = commentService.getComments(postId, before, size);
        return ResponseEntity.ok(
                comments.stream()
                        .map(CommentResponse::fromDomain)
                        .toList()
        );
    }

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            HttpServletRequest request,
            @PathVariable UUID postId,
            @Valid @RequestBody CreateCommentCommand cmd
    ) {

        if (!postService.existsById(postId)) {
            throw new PostNotFoundException(postId.toString());
        }

        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        Comment comment = commentService.createComment(postId, currUserId, cmd);
        
        return ResponseEntity.ok(CommentResponse.fromDomain(comment));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(HttpServletRequest request, @PathVariable UUID commentId) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        commentService.deleteComment(commentId, currUserId, false);
    }
}
