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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.application.validation.PostMediaValidator;
import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.AuthorResponse;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse2;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.user.domain.model.User;
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

    @GetMapping("/explore")
    public ResponseEntity<List<PostResponse>> getAllPosts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {

        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        List<Post> posts = postService.findAll(pageable);

        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    User postUser = userService.findById(post.getUserId());
                    String avatarUrl = mediaService.getAvatarUrl(postUser.getAvatarMediaId());
                    List<Media> mediaList = mediaService.findByPostId(post.getId());
                    Boolean isOwner = currUserId.equals(post.getUserId());
                    AuthorResponse author = AuthorResponse.fromDomain(postUser, avatarUrl);

                    return PostResponse.fromDomain(
                            post,
                            author,
                            isOwner,
                            true,
                            mediaList
                    );
                })
                .toList();

        return ResponseEntity.ok(responses);
    }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<List<PostResponse>> getPostByUserId(
    //         HttpServletRequest request,
    //         @PathVariable UUID userId,
    //         @RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "10") int size,
    //         @RequestParam(defaultValue = "createdAt") String sortBy
    // ) {

    //     Sort sort = Sort.by(sortBy).descending();
    //     Pageable pageable = PageRequest.of(page, size, sort);

    //     UUID currUserId = jwtService.extractUserIdFromRequest(request);

    //     List<Post> posts = postService.findByUserId(userId, pageable);

    //     List<PostResponse> responses = posts.stream()
    //             .map(post -> {
    //                 User postUser = userService.findById(post.getUserId());
    //                 String avatarUrl = mediaService.getAvatarUrl(postUser.getAvatarMediaId());
    //                 List<Media> mediaList = mediaService.findByPostId(post.getId());
    //                 Boolean isOwner = currUserId.equals(post.getUserId());
    //                 AuthorResponse author = AuthorResponse.fromDomain(postUser, avatarUrl);

    //                 return PostResponse.fromDomain(
    //                         post,
    //                         author,
    //                         isOwner,
    //                         true,
    //                         mediaList
    //                 );
    //             })
    //             .toList();
    //     return ResponseEntity.ok(responses);
    // }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUserUsername(
            HttpServletRequest request,
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        UUID currUserId = jwtService.extractUserIdFromRequest(request);

        List<Post> posts = postService.findByUserUsername(username, pageable);

        List<PostResponse> responses = posts.stream()
                .map(post -> {
                    User postUser = userService.findById(post.getUserId());
                    String avatarUrl = mediaService.getAvatarUrl(postUser.getAvatarMediaId());
                    List<Media> mediaList = mediaService.findByPostId(post.getId());
                    Boolean isOwner = currUserId.equals(post.getUserId());
                    AuthorResponse author = AuthorResponse.fromDomain(postUser, avatarUrl);

                    return PostResponse.fromDomain(
                            post,
                            author,
                            isOwner,
                            true,
                            mediaList
                    );
                })
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}")
    public PostResponse2 getPost(@PathVariable UUID postId, HttpServletRequest request) {
        List<Media> mediaList = mediaService.findByPostId(postId);
        return PostResponse2.fromDomain(postService.findById(postId), mediaList);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse2> createPost(
            HttpServletRequest request,
            @Valid @ModelAttribute CreatePostCommand cmd
    ) {
        UUID userId = jwtService.extractUserIdFromRequest(request);

        for (MultipartFile file : cmd.files()) {
            mediaValidator.validate(file);
        }

        Post createdPost = postService.createPost(cmd, userId);
        List<Media> mediaList = mediaService.findByPostId(createdPost.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse2.fromDomain(createdPost, mediaList));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse2> updatePost(
            @RequestBody @Valid UpdatePostCommand cmd,
            HttpServletRequest request,
            @PathVariable UUID postId
    ) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        Post post = postService.updatePost(postId, cmd, currUserId, false);
        List<Media> mediaList = mediaService.findByPostId(postId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(PostResponse2.fromDomain(post, mediaList));
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
