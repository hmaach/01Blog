package com.blog.domain.port.in;

import java.util.List;

import com.blog.application.dto.request.CreateUserCommand;
import com.blog.application.dto.request.UpdateUserCommand;
import com.blog.domain.model.User;

public interface UserService {

    User createUser(CreateUserCommand command);

    User findById(String id);

    User findByEmail(String email);

    List<User> findAll();

    User updateUser(String id, UpdateUserCommand command);

    void deleteUser(String id);
}
