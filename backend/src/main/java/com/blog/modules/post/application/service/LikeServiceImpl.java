package com.blog.modules.post.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.port.in.LikeService;
import com.blog.modules.post.domain.port.out.LikeRepository;

import jakarta.transaction.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(
            LikeRepository likeRepository
    ) {
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional
    public Integer likePost(UUID postId, UUID userId) {
        return likeRepository.likePost(postId, userId);
    }

}
