package com.blog.modules.user.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.CurrentUserResponse;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final MediaService mediaService;
    private final JwtService jwtService;

    public UserController(
            UserService userService,
            MediaService mediaService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.mediaService = mediaService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public CurrentUserResponse getCurrentUser(HttpServletRequest request) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        User user = userService.findById(currUserId);
        // String avatarUrl = mediaService.getAvatarUrl(user.getAvatarMediaId());
        return CurrentUserResponse.fromDomain(user);
    }

    @GetMapping("/all")
    public List<UserResponse> getUsers(
            HttpServletRequest request,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.findAll(query, before, size).stream()
                .map(UserResponse::fromDomain)
                .toList();
    }

    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return UserResponse.fromDomain(userService.findById(id));
    }

    @GetMapping("/{username}")
    public UserProfileResponse getUserByUsername(HttpServletRequest request, @PathVariable String username) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        String relation;

        User user = userService.findByUsername(username);

        if (currUserId.equals(user.getId())) {
            relation = "owner";
        } else {
            if (userService.isSubscribed(currUserId, user.getId())) {
                relation = "subscribed";
            } else {
                relation = "unsubscribed";
            }
        }

        return UserProfileResponse.fromDomain(user, relation);
    }

    @GetMapping("/{userId}/readme")
    public String getUserReadme(HttpServletRequest request, @PathVariable UUID userId) {
        if (!userService.userExist(userId)) {
            throw new UserNotFoundException(userId.toString());
        }
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        return userService.getUserReadme(currUserId, userId);
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse updateUser(HttpServletRequest request, @Valid @ModelAttribute UpdateUserCommand cmd) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        return UserResponse.fromDomain(userService.updateUserInfo(currUserId, cmd));
    }

    @PostMapping("/subscribe/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subscribeToUser(HttpServletRequest request, @PathVariable UUID targetUserId) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        userService.subscribeToUser(currUserId, targetUserId);
    }

    @DeleteMapping("/subscribe/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribeToUser(HttpServletRequest request, @PathVariable UUID targetUserId) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        userService.unsubscribeToUser(currUserId, targetUserId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest request) {
        UUID currUserId = jwtService.extractUserIdFromRequest(request);
        userService.deleteUser(currUserId);
    }

}
