package com.blog.modules.user.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.user.domain.event.UserCreatedPostEvent;
import com.blog.modules.user.domain.event.UserDeletedPostEvent;
import com.blog.modules.user.domain.port.in.UserService;

@Component
public class UserPostsEventListener {

    private final UserService userService;

    public UserPostsEventListener(UserService userService) {
        this.userService = userService;
    }

    @Async
    @EventListener
    public void handleUserCreatedPost(UserCreatedPostEvent event) {
        userService.incrementPostsCount(event.userId());
    }

    @Async
    @EventListener
    public void handleUserDeletedPost(UserDeletedPostEvent event) {
        userService.decrementPostsCount(event.userId());
    }
}
