package com.blog.modules.post.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostFetchedEvent;
import com.blog.modules.post.domain.port.in.PostService;

@Component
public class PostFetchedEventListener {

    private final PostService postService;

    public PostFetchedEventListener(PostService postService) {
        this.postService = postService;
    }

    @Async
    @EventListener
    public void handlePostFetched(PostFetchedEvent event) {
        postService.incrementImpressionsCount(event.postId());
    }

}
