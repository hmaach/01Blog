package com.blog.modules.user.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.modules.user.domain.exception.EmailAlreadyExistsException;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.exception.UsernameAlreadyExistsException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;

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
    public User findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return user;
    }

    // TODO : implement userExist
    @Override
    public Boolean userExist(UUID id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public boolean userExistByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public String getUserReadme(UUID userId) {
        return userRepository.getUserReadme(userId);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
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

        if (cmd.getUsername() != null && !user.getUsername().equals(cmd.getUsername())) {
            if (userRepository.findByUsername(cmd.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(cmd.getUsername());
            }
            user.changeUsername(cmd.getUsername());
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
