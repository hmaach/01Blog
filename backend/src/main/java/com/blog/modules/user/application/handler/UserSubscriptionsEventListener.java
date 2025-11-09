package com.blog.modules.user.application.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blog.modules.user.domain.event.UserWasSubscribedEvent;
import com.blog.modules.user.domain.event.UserWasUnsubscribedEvent;
import com.blog.modules.user.domain.port.in.UserService;

@Component
public class UserSubscriptionsEventListener {

    private final UserService userService;

    public UserSubscriptionsEventListener(UserService userService) {
        this.userService = userService;
    }

    @Async
    @EventListener
    public void handleUserSubscribed(UserWasSubscribedEvent event) {
        userService.incrementSubscriptionsCount(event.userId());
    }

    @Async
    @EventListener
    public void handleUserUnsubscribed(UserWasUnsubscribedEvent event) {
        userService.decrementSubscriptionsCount(event.userId());
    }
}
