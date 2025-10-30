package com.blog.modules.post.application.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.event.PostLikedEvent;
import com.blog.modules.post.domain.event.PostUnlikedEvent;
import com.blog.modules.post.domain.port.in.LikeService;
import com.blog.modules.post.domain.port.out.LikeRepository;
import com.blog.modules.post.infrastructure.adapter.out.persistence.like.LikeEntity;

import jakarta.transaction.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LikeServiceImpl(
            LikeRepository likeRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.likeRepository = likeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void likePost(UUID postId, UUID userId) {
        boolean exists = likeRepository.existsByUserIdAndPostId(userId, postId);

        if (!exists) {
            likeRepository.save(new LikeEntity(userId, postId));
            eventPublisher.publishEvent(new PostLikedEvent(postId));
        } else {
            likeRepository.delete(userId, postId);
            eventPublisher.publishEvent(new PostUnlikedEvent(postId));
        }
    }

}
