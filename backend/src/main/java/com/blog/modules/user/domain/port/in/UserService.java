package com.blog.modules.user.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.blog.modules.admin.infrastructure.adapter.in.web.dto.CreateUserCommand;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.domain.model.User;

public interface UserService {

    User createUser(CreateUserCommand command);

    User findById(UUID id);

    User findByEmail(String email);

    List<User> findAll();

    // TODO: separate between the update of the user and the admin
    User updateUser(UUID id, UpdateUserCommand command);

    void deleteUser(UUID id);
}
