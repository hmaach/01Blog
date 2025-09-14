package com.letsplay.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.letsplay.application.dto.request.LoginUserCommand;
import com.letsplay.application.dto.request.RegisterUserCommand;
import com.letsplay.application.dto.response.UserResponse;
import com.letsplay.application.exception.EmailAlreadyExistsException;
import com.letsplay.domain.model.User;
import com.letsplay.domain.port.in.AuthService;
import com.letsplay.infrastructure.persistence.UserRepositoryImpl;
import com.letsplay.infrastructure.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse register(RegisterUserCommand cmd) {
        if (!userRepository.findByEmail(cmd.email()).isEmpty()) {
            throw new EmailAlreadyExistsException(cmd.email());
        }

        String id = UUID.randomUUID().toString();

        User user = new User(
                id,
                cmd.name(),
                cmd.email(),
                encoder.encode(cmd.password()),
                "USER",
                LocalDateTime.now()
        );

        userRepository.save(user);

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
