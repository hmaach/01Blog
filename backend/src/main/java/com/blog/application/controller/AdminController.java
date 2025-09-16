package com.blog.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.user.domain.service.UserDomainService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.CreateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final UserDomainService service;

    public AdminController(UserDomainService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return UserResponse.fromDomain(service.findById(id));
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return service.findAll().stream()
                .map(UserResponse::fromDomain)
                .toList();
    }

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid CreateUserCommand cmd) {
        return UserResponse.fromDomain(service.createUser(cmd));
    }

    @PatchMapping("/{id}")
    public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserCommand cmd) {
        return UserResponse.fromDomain(service.updateUser(id, cmd));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }
}
