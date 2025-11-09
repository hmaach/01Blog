package com.blog.modules.user.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.user.domain.event.UserFetchedEvent;
import com.blog.modules.user.domain.port.in.UserService;

@Component
public class UserFetchedEventListener {

    private final UserService userService;

    public UserFetchedEventListener(UserService userService) {
        this.userService = userService;
    }

    @Async
    @EventListener
    public void handleUserFetched(UserFetchedEvent event) {
        userService.incrementImpressionsCount(event.userId());
    }

}
