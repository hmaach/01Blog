package com.blog.modules.user.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.blog.modules.user.domain.model.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    Optional<UUID> getAvatarId(UUID userId);

    void updateAvatarId(UUID userId, UUID avatarUrl);

    void ban(UUID userId);

    void unban(UUID userId);

    void deleteById(UUID id);
}
