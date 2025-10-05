package com.blog.modules.post.infrastructure.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.shared.infrastructure.security.JwtService;
import com.blog.shared.validation.MediaValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final MediaService mediaService;
    private final MediaValidator mediaValidator;

    public UserPostController(
            PostService postService,
            JwtService jwtService,
            MediaService mediaService,
            MediaValidator mediaValidator
    ) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.mediaService = mediaService;
        this.mediaValidator = mediaValidator;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {

        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Post> posts = postService.findAll(pageable);
        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    List<Media> mediaList = mediaService.findByPostId(post.getId());
                    return PostResponse.fromDomain(post, mediaList);
                })
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostByUser(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {

        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Post> posts = postService.findByUserId(userId, pageable);
        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    List<Media> mediaList = mediaService.findByPostId(post.getId());
                    return PostResponse.fromDomain(post, mediaList);
                })
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable UUID postId, HttpServletRequest request) {
        List<Media> mediaList = mediaService.findByPostId(postId);
        return PostResponse.fromDomain(postService.findById(postId), mediaList);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(
            HttpServletRequest request,
            @Valid @ModelAttribute CreatePostCommand cmd
    ) {
        UUID userId = UUID.fromString(jwtService.extractUserIdFromRequest(request));

        for (MultipartFile file : cmd.files()) {
            mediaValidator.validatePostMedia(file);
        }

        Post createdPost = postService.createPost(cmd, userId);
        List<Media> mediaList = mediaService.findByPostId(createdPost.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.fromDomain(createdPost, mediaList));
    }

    @PostMapping("/like/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likePost(@PathVariable UUID postId, HttpServletRequest request) {
        UUID currentUserId = UUID.fromString(jwtService.extractUserIdFromRequest(request));
        postService.likePost(postId, currentUserId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID postId, HttpServletRequest request) {
        UUID currentUserId = UUID.fromString(jwtService.extractUserIdFromRequest(request));
        postService.deletePost(postId, currentUserId, false);
    }
}
