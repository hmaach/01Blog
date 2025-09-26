package com.blog.modules.user.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(UUID id);
}
