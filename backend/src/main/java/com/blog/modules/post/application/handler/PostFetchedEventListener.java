package com.blog.modules.post.application.handler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostFetchedEvent;
import com.blog.modules.post.domain.event.PostsFetchedEvent;
import com.blog.modules.post.domain.model.Post;
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
        postService.incrementImpressionsCount(List.of(event.postId()));
    }

    @Async
    @EventListener
    public void handlePostsFetched(PostsFetchedEvent event) {
        List<UUID> postIds = event.posts().stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        postService.incrementImpressionsCount(postIds);
    }

}
