package com.blog.application.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.service.UserServiceImpl;
import com.blog.modules.user.dto.UpdateUserCommand;
import com.blog.modules.user.dto.UserResponse;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/me")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    public UserController(UserServiceImpl userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public UserResponse getUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.findById(id));
    }

    @PatchMapping
    public UserResponse updateUser(HttpServletRequest request, @Valid @RequestBody UpdateUserCommand cmd) {
        String id = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.updateUser(id, cmd));
    }

    @DeleteMapping
    public void deleteUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);
        userService.deleteUser(id);
    }

}
