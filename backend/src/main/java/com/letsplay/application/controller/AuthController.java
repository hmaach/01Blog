package com.letsplay.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsplay.application.dto.request.LoginUserCommand;
import com.letsplay.application.dto.request.RegisterUserCommand;
import com.letsplay.application.dto.response.UserResponse;
import com.letsplay.domain.service.AuthServiceImpl;

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
