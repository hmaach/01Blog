package com.blog.modules.user.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;

public interface UserService {

    User findById(UUID userId);

    Boolean userExist(UUID userId);

    boolean userExistByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    String getUserReadme(UUID currentUserId, UUID userId);

    User updateUser(UUID userId, UpdateUserCommand command);

    void subscribeToUser(UUID currUserId, UUID targetUserId);

    void unsubscribeToUser(UUID currUserId, UUID targetUserId);

    void incrementImpressionsCount(UUID userId);

    void incrementSubscriptionsCount(UUID userId);

    void decrementSubscriptionsCount(UUID userId);

    void incrementPostsCount(UUID userId);

    void decrementPostsCount(UUID userId);

    void deleteUser(UUID userId);

}
