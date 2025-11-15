package com.blog.modules.post.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostLikedEvent;
import com.blog.modules.post.domain.event.PostUnlikedEvent;
import com.blog.modules.post.domain.port.in.PostService;

@Component
public class PostLikeEventListener {

    private final PostService postService;

    public PostLikeEventListener(PostService postService) {
        this.postService = postService;
    }

    @Async
    @EventListener
    public void handlePostLiked(PostLikedEvent event) {
        postService.incrementLikesCount(event.postId());
    }
    
    @Async
    @EventListener
    public void handlePostUnliked(PostUnlikedEvent event) {
        postService.decrementLikesCount(event.postId());
    }
}
