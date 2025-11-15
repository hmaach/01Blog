package com.blog.modules.post.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostCreatedCommentEvent;
import com.blog.modules.post.domain.event.PostDeletedCommentEvent;
import com.blog.modules.post.domain.port.in.PostService;

@Component
public class PostCommentEventListener {

    private final PostService postService;

    public PostCommentEventListener(PostService postService) {
        this.postService = postService;
    }

    @Async
    @EventListener
    public void handleCommentCreated(PostCreatedCommentEvent event) {
        postService.incrementCommentsCount(event.postId());
    }

    @Async
    @EventListener
    public void handleCommentDeleted(PostDeletedCommentEvent event) {
        postService.decrementCommentsCount(event.postId());
    }
}
