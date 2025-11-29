package com.blog.modules.admin.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blog.modules.admin.domain.port.in.AdminUserService;
import com.blog.modules.user.domain.port.out.UserRepository;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    public AdminUserServiceImpl(UserRepository userRepository) {
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
    public void DeleteUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        userRepository.deleteById(userId);
    }

}
