package com.blog.modules.user.application.handler;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostFetchedEvent;
import com.blog.modules.user.domain.port.in.UserService;

@Component
public class UserFetchedEventListener {

    private final UserService userService;

    public UserFetchedEventListener(UserService userService) {
        this.userService = userService;
    }

    @Async
    @EventListener
    public void handlePostFetched(PostFetchedEvent event) {
        userService.incrementImpressionsCount(List.of(event.postId()));
    }

}
