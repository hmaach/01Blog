package com.blog.modules.user.domain.port.in;

import java.util.List;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.CreateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;

public interface UserService {

    User createUser(CreateUserCommand command);

    User findById(String id);

    User findByEmail(String email);

    List<User> findAll();

    User updateUser(String id, UpdateUserCommand command);

    void deleteUser(String id);
}
