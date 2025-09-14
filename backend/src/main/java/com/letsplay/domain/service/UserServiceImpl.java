package com.letsplay.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.letsplay.application.dto.request.CreateUserCommand;
import com.letsplay.application.dto.request.UpdateUserCommand;
import com.letsplay.application.exception.EmailAlreadyExistsException;
import com.letsplay.application.exception.UserNotFoundException;
import com.letsplay.domain.model.User;
import com.letsplay.domain.port.in.UserService;
import com.letsplay.infrastructure.persistence.UserRepositoryImpl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepositoryImpl userRepository) {
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

        String id = UUID.randomUUID().toString();

        User user = new User(
                id,
                cmd.name(),
                cmd.email(),
                encoder.encode(cmd.password()),
                cmd.role() != null ? cmd.role() : "USER",
                LocalDateTime.now()
        );

        userRepository.save(user);

        return user;

    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return user;
    }

    @Override
    public User updateUser(String id, UpdateUserCommand cmd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

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
    public void deleteUser(String id) {
        @SuppressWarnings("unused")
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

}
