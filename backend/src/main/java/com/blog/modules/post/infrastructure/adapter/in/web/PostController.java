package com.blog.modules.post.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.media.application.validation.PostMediaValidator;
import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final JwtService jwtService;
    private final MediaService mediaService;
    private final PostMediaValidator mediaValidator;

    public PostController(
            PostService postService,
            UserService userService,
            JwtService jwtService,
            MediaService mediaService,
            PostMediaValidator mediaValidator
    ) {
        this.postService = postService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.mediaService = mediaService;
        this.mediaValidator = mediaValidator;
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeedPosts(
            HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        List<Post> posts = postService.findFeedPosts(currUserId, before, size);

        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    Boolean isOwner = currUserId.equals(post.getUserId());

                    return PostResponse.fromDomain(post, isOwner);
                }).toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/explore")
    public ResponseEntity<List<PostResponse>> getAllPosts(
            HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        List<Post> posts = postService.findAll(before, size);

        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    Boolean isOwner = currUserId.equals(post.getUserId());

                    return PostResponse.fromDomain(post, isOwner);
                }).toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUserUsername(
            HttpServletRequest request,
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        List<Post> posts = postService.findByUserUsername(username, before, size);

        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    Boolean isOwner = currUserId.equals(post.getUserId());

                    return PostResponse.fromDomain(post, isOwner);
                }).toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable UUID postId, HttpServletRequest request) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        Post post = postService.findById(postId);

        List<Media> mediaList = mediaService.findByPostId(postId);
        Boolean isOwner = currUserId.equals(post.getUserId());

        return PostResponse.fromDomain(post, isOwner, mediaList);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            HttpServletRequest request,
            @Valid CreatePostCommand cmd
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        Post createdPost = postService.createPost(cmd, currUserId);
        List<Media> mediaList = new ArrayList<>();

        if (cmd.medias() != null && !cmd.medias().isEmpty()) {
            mediaList = mediaService.findByPostId(createdPost.getId());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.fromDomain(
                        createdPost,
                        true,
                        mediaList
                ));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @Valid UpdatePostCommand cmd,
            HttpServletRequest request,
            @PathVariable UUID postId
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        Post post = postService.updatePost(postId, cmd, currUserId, false);
        List<Media> mediaList = mediaService.findByPostId(postId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(PostResponse.fromDomain(
                        post,
                        true,
                        mediaList
                ));
    }

    @PostMapping("/like/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likePost(@PathVariable UUID postId, HttpServletRequest request) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);
        postService.likePost(postId, currentUserId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID postId, HttpServletRequest request) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);
        postService.deletePost(postId, currentUserId, false);
    }
}
