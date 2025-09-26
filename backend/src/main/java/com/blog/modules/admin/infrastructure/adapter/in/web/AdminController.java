package com.blog.modules.admin.infrastructure.adapter.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.user.application.service.UserServiceImpl;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final UserServiceImpl service;

    public AdminController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        return UserResponse.fromDomain(service.findById(id));
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return service.findAll().stream()
                .map(UserResponse::fromDomain)
                .toList();
    }

    // @PostMapping
    // public UserResponse createUser(@RequestBody @Valid CreateUserCommand cmd) {
    //     return UserResponse.fromDomain(service.createUser(cmd));
    // }

    // @PatchMapping("/{id}")
    // public UserResponse updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserCommand cmd) {
    //     return UserResponse.fromDomain(service.updateUser(id, cmd));
    // }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
    }
}
