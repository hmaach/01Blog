package com.blog.modules.user.infrastructure.adapter.in.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.user.application.service.AuthServiceImpl;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.LoginUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.RegisterUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthServiceImpl service;

    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse register(@Valid @ModelAttribute RegisterUserCommand cmd) {
        return service.register(cmd);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginUserCommand cmd) {
        return service.login(cmd);
    }
}
