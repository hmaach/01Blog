package com.blog.modules.user.domain.model;

import java.util.UUID;

public class Subscription {

    private final UUID subscriberId;
    private final UUID subscribedToId;

    public Subscription(UUID subscriberId, UUID subscribedToId) {
        this.subscriberId = subscriberId;
        this.subscribedToId = subscribedToId;
    }

    public UUID getSubscriberId() {
        return subscriberId;
    }

    public UUID getSubscribedToId() {
        return subscribedToId;
    }
}
