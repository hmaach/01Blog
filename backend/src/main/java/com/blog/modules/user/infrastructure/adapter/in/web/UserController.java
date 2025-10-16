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

import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.user.application.service.UserServiceImpl;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final MediaService mediaService;
    private final JwtService jwtService;

    public UserController(
            UserServiceImpl userService,
            MediaService mediaService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.mediaService = mediaService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public UserResponse getCurrentUser(HttpServletRequest request) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.findById(currUserId));
    }

    @GetMapping("/all")
    public List<UserResponse> getUsers() {
        return userService.findAll().stream()
                .map(UserResponse::fromDomain)
                .toList();
    }

    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return UserResponse.fromDomain(userService.findById(id));
    }

    @GetMapping("/{username}")
    public UserProfileResponse getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        String avatarUrl = mediaService.getAvatarUrl(user.getAvatarMediaId());

        return UserProfileResponse.fromDomain(user, avatarUrl, "tt");
    }

    @GetMapping("/{userId}/readme")
    public String getUserReadme(@PathVariable UUID userId) {
        if (!userService.userExist(userId)) {
            throw new UserNotFoundException(userId.toString());
        }
        return userService.getUserReadme(userId);
    }

    @PatchMapping
    public UserResponse updateUser(HttpServletRequest request, @Valid @RequestBody UpdateUserCommand cmd) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.updateUser(currUserId, cmd));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest request) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        userService.deleteUser(currUserId);
    }

}
