package com.blog.modules.post.infrastructure.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.service.PostDomainService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostDomainService postService;
    private final JwtService jwtService;

    public PostController(
            PostDomainService postService,
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

    @GetMapping("/user")
    public List<PostResponse> getPostsOfCurrentUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);

        return postService.findByUserId(id).stream()
                .map(PostResponse::fromDomain)
                .toList();
    }

    @GetMapping("/user/{id}")
    public List<PostResponse> getPostsByUserId(@PathVariable String id) {
        return postService.findByUserId(id).stream()
                .map(PostResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable String id) {
        return PostResponse.fromDomain(postService.findById(id));
    }

    @PostMapping
    public PostResponse createPost(@RequestBody @Valid CreatePostCommand cmd, HttpServletRequest request) {
        String userId = jwtService.extractUserIdFromRequest(request);
        return PostResponse.fromDomain(postService.createPost(cmd, userId));
    }

    @PatchMapping("/{id}")
    public PostResponse updatePost(
            @PathVariable String id,
            @RequestBody UpdatePostCommand cmd,
            HttpServletRequest request) {

        String userId = jwtService.extractUserIdFromRequest(request);
        String role = jwtService.extractRoleFromRequest(request);
        boolean isAdmin = role.equals("ADMIN");

        Post updated = postService.updatePost(id, cmd, userId, isAdmin);
        return PostResponse.fromDomain(updated);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id, HttpServletRequest request) {

        String userId = jwtService.extractUserIdFromRequest(request);
        String role = jwtService.extractUserIdFromRequest(request);
        boolean isAdmin = role.equals("ADMIN");

        System.err.println("---------------------" + role + "---------------------");

        postService.deletePost(id, userId, isAdmin);
    }
}
