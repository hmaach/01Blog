package com.blog.modules.user.domain.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.modules.admin.infrastructure.adapter.in.web.dto.CreateUserCommand;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.domain.exception.EmailAlreadyExistsException;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;

@Service
public class UserDomainService implements UserService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDomainService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(CreateUserCommand cmd) {
        if (!userRepository.findByEmail(cmd.email()).isEmpty()) {
            throw new EmailAlreadyExistsException(cmd.email());
        }

        UUID id = UUID.randomUUID();

        User user = new User(
                id,
                cmd.name(),
                cmd.email(),
                encoder.encode(cmd.password()),
                cmd.role() != null ? cmd.role() : "USER",
                Instant.now()
        );

        userRepository.save(user);

        return user;

    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return user;
    }

    @Override
    public User updateUser(UUID id, UpdateUserCommand cmd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        if (cmd.getName() != null) {
            user.changeName(cmd.getName());
        }

        if (cmd.getEmail() != null && !user.getEmail().equals(cmd.getEmail())) {
            if (userRepository.findByEmail(cmd.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException(cmd.getEmail());
            }
            user.changeEmail(cmd.getEmail());
        }

        if (cmd.getPassword() != null) {
            user.changePassword(encoder.encode(cmd.getPassword()));
        }

        if (cmd.getRole() != null) {
            user.changeRole(cmd.getRole());
        }

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.deleteById(id);
    }

}
