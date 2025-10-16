package com.blog.modules.user.domain.port.in;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;

public interface UserService {

    User findById(UUID id);

    public Optional<User> userExist(UUID id);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    User updateUser(UUID id, UpdateUserCommand command);

    void deleteUser(UUID id);
}
