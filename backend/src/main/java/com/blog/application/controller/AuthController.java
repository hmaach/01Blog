package com.blog.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.service.AuthServiceImpl;
import com.blog.modules.user.dto.LoginUserCommand;
import com.blog.modules.user.dto.RegisterUserCommand;
import com.blog.modules.user.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthServiceImpl service;

    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid RegisterUserCommand cmd) {
        return service.register(cmd);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginUserCommand cmd) {
        return service.login(cmd);
    }
}
