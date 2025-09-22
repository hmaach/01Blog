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
    public void HidePost(UUID PostId) {
        postRepository.findById(PostId)
                .orElseThrow(() -> new PostNotFoundException(PostId.toString()));
        postRepository.hidePostById(PostId);
    }

    @Override
    public void DeletePost(UUID PostId) {
        postRepository.findById(PostId)
                .orElseThrow(() -> new PostNotFoundException(PostId.toString()));
        postRepository.deleteById(PostId);
    }

}
