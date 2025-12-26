package com.blog.modules.user.infrastructure.adapter.out.persistence.notification;

import com.blog.modules.user.domain.model.Notification;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserMapper;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = entity.getUser() != null
                ? UserMapper.toDomain(entity.getUser())
                : null;

        return new Notification(
                entity.getId(),
                entity.getUserId(),
                user,
                entity.getPostId(),
                entity.getSeen(),
                entity.getCreatedAt());
    }
}
