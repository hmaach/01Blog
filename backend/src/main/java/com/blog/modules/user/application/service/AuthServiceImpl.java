package com.blog.modules.user.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.modules.media.application.validation.AvatarMediaValidator;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.user.domain.exception.EmailAlreadyExistsException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.AuthService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.LoginUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.RegisterUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;
import com.blog.shared.infrastructure.exception.InternalServerErrorException;
import com.blog.shared.infrastructure.security.JwtService;

import io.jsonwebtoken.io.IOException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private final MediaService mediaService;
    private final AvatarMediaValidator avatarMediaValidator;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(
            UserRepositoryImpl userRepository,
            MediaService mediaService,
            AvatarMediaValidator avatarMediaValidator
    ) {
        this.userRepository = userRepository;
        this.mediaService = mediaService;
        this.avatarMediaValidator = avatarMediaValidator;
    }

    @Override
    public UserResponse register(RegisterUserCommand cmd) {
        if (!userRepository.findByEmail(cmd.email()).isEmpty()) {
            throw new EmailAlreadyExistsException(cmd.email());
        }

        UUID userId = UUID.randomUUID();
        UUID mediaId;

        User user = new User(
                userId,
                cmd.name(),
                "username",
                cmd.email(),
                encoder.encode(cmd.password()),
                "USER",
                Instant.now()
        );

        userRepository.save(user);

        if (cmd.avatar() != null) {
            avatarMediaValidator.validate(cmd.avatar());
            try {
                mediaId = mediaService.uploadAvatar(userId, cmd.avatar());
                user.changeAvatar(mediaId);
            } catch (IOException | java.io.IOException | IllegalStateException e) {
                throw new InternalServerErrorException("Failed to upload avatar: " + e.getMessage());
            }
        }

        return UserResponse.fromDomain(user);
    }

    @Override
    public String login(LoginUserCommand cmd) {
        Authentication auth = authManager.
                authenticate(new UsernamePasswordAuthenticationToken(cmd.email(), cmd.password()));

        if (auth.isAuthenticated()) {
            User user = userRepository.findByEmail(cmd.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return jwtService.generateToken(user);
        }
        return "";
    }

}
