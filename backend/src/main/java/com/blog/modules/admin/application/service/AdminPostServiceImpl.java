package com.blog.modules.admin.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.blog.modules.admin.domain.port.in.AdminPostService;
import com.blog.modules.post.domain.exception.PostNotFoundException;
import com.blog.modules.post.infrastructure.adapter.out.persistence.PostRepositoryImpl;

public class AdminPostServiceImpl implements AdminPostService {

    @Autowired
    private final PostRepositoryImpl postRepository;

    public AdminPostServiceImpl(PostRepositoryImpl postRepository) {
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
