package com.blog.modules.user.infrastructure.adapter.out.persistence.notification;

import com.blog.modules.user.domain.model.Notification;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Notification(
                entity.getId(),
                entity.getUserId(),
                entity.getUserOwnerId(),
                entity.getPostId(),
                entity.getCreatedAt()
        );
    }
}
