package com.blog.modules.admin.application.service;

import java.util.UUID;

import com.blog.modules.admin.domain.port.in.AdminPostService;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.exception.PostNotFoundException;

public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    public AdminPostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void HidePost(UUID postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        postRepository.hidePostById(postId);
    }

    @Override
    public void DeletePost(UUID postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        postRepository.deleteById(postId);
    }

}
