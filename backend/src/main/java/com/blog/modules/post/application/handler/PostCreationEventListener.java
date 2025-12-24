package com.blog.modules.post.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.post.domain.event.PostCreatedEvent;
import com.blog.modules.user.domain.port.in.UserService;

@Component
public class PostCreationEventListener {

    private final UserService userService;

    public PostCreationEventListener(UserService userService) {
        this.userService = userService;
    }

    @Async
    @EventListener
    public void handlePostCreated(PostCreatedEvent event) {
        userService.createNotifications(event.userId(), event.postId());
    }
}
