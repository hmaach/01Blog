package com.blog.modules.admin.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.modules.admin.domain.port.in.AdminUserService;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.UpdateUserRoleCommand;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    public AdminUserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void BanUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        userRepository.ban(userId);

    }

    @Override
    public void UnbanUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        userRepository.unban(userId);

    }

    @Override
    public void ChangeUserRole(UUID userId, UpdateUserRoleCommand cmd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (cmd.role() != null) {
            user.changeRole(cmd.role());
        }

        userRepository.save(user);
    }

    @Override
    public void DeleteUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        userRepository.deleteById(userId);
    }

}
