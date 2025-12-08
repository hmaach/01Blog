package com.blog.modules.user.infrastructure.adapter.out.persistence.user;

import java.util.UUID;

import com.blog.modules.media.infrastructure.adapter.out.persistence.MediaEntity;
import com.blog.modules.user.domain.model.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        MediaEntity avatar = new MediaEntity();
        avatar.setId(user.getAvatarMediaId());

        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getEmailVerified(),
                user.getPassword(),
                user.getRole(),
                user.getStatus(),
                user.getPostsCount(),
                user.getSubscribersCount(),
                user.getImpressionsCount(),
                avatar,
                user.getReadme(),
                user.getCreatedAt()
        );
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UUID avatarId = entity.getAvatar() != null
                ? entity.getAvatar().getId()
                : null;

        String avatarUrl = entity.getAvatar() != null
                ? entity.getAvatar().getUrl()
                : null;

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getEmailVerified(),
                entity.getPassword(),
                entity.getRole(),
                entity.getStatus(),
                entity.getPostsCount(),
                entity.getSubscribersCount(),
                entity.getImpressionsCount(),
                avatarId,
                avatarUrl,
                entity.getReadme(),
                entity.getCreatedAt()
        );
    }
}
