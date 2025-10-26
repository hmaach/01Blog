package com.blog.modules.user.infrastructure.adapter.out.persistence;

import com.blog.modules.user.domain.model.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getStatus(),
                user.getPostsCount(),
                user.getSubscribersCount(),
                user.getImpressionsCount(),
                user.getReadme(),
                user.getCreatedAt()
        );
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getStatus(),
                entity.getPostsCount(),
                entity.getSubscribersCount(),
                entity.getImpressionsCount(),
                entity.getAvatarMediaId(),
                entity.getReadme(),
                entity.getCreatedAt()
        );
    }
}
