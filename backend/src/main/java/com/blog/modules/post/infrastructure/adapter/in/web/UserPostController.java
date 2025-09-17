package com.blog.modules.post.infrastructure.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.post.infrastructure.adapter.in.web.dto.PostResponse;
import com.blog.modules.post.infrastructure.service.PostServiceImpl;
import com.blog.shared.infrastructure.security.JwtService;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    private final PostServiceImpl postService;
    private final JwtService jwtService;

    public UserPostController(
            PostServiceImpl postService,
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
}
