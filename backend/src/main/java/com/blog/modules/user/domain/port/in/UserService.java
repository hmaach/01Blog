package com.blog.modules.user.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;

public interface UserService {

    User findById(UUID id);

    Boolean userExist(UUID id);

    boolean userExistByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    String getUserReadme(UUID userId);

    User updateUser(UUID id, UpdateUserCommand command);

    void subscribeToUser(UUID currUserId, UUID targetUserId);

    void unsubscribeToUser(UUID currUserId, UUID targetUserId);

    void deleteUser(UUID id);

    void incrementImpressionsCount(List<UUID> of);

}
