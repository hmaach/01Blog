package com.blog.modules.user.infrastructure.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.user.application.service.UserServiceImpl;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    public UserController(UserServiceImpl userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public UserResponse getCurrentUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.findById(UUID.fromString(id)));
    }

    @GetMapping("/all")
    public List<UserResponse> getUsers() {
        return userService.findAll().stream()
                .map(UserResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return UserResponse.fromDomain(userService.findById(id));
    }

    @PatchMapping
    public UserResponse updateUser(HttpServletRequest request, @Valid @RequestBody UpdateUserCommand cmd) {
        String id = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.updateUser(UUID.fromString(id), cmd));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);
        userService.deleteUser(UUID.fromString(id));
    }

}
