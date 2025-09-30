package com.blog.modules.post.infrastructure.adapter.in.web;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.shared.infrastructure.security.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    private final PostService postService;
    private final JwtService jwtService;

    public UserPostController(
            PostService postService,
            JwtService jwtService
    ) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.findAll().stream()
                .map(PostResponse::fromDomain)
                .toList();
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            HttpServletRequest request,
            @Valid @RequestBody CreatePostCommand cmd
    ) {
        UUID userId = UUID.fromString(jwtService.extractUserIdFromRequest(request));
            Post createdPost = postService.createPost(cmd, userId);
            

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.fromDomain(createdPost));
    }

}
