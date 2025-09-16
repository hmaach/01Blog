package com.blog.modules.user.domain.port.in;

import java.util.List;

import com.blog.modules.admin.infrastructure.adapter.in.web.dto.CreateUserCommand;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.domain.model.User;

public interface UserService {

    User createUser(CreateUserCommand command);

    User findById(String id);

    User findByEmail(String email);

    List<User> findAll();

    // TODO: separate between the update of the user and the admin
    User updateUser(String id, UpdateUserCommand command);

    void deleteUser(String id);
}
