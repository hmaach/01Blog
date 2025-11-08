package com.blog.modules.user.domain.port.out;

import java.util.UUID;

public interface SubscriptionRepository {

    boolean isSubscribed(UUID currUserId, UUID targetUserId);

    void subscribe(UUID currUserId, UUID targetUserId);

    void unsubscribe(UUID currUserId, UUID targetUserId);
}
