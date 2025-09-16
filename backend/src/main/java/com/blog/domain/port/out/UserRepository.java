package com.blog.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.blog.domain.model.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void deleteById(String id);
}
