package com.blog.modules.user.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.modules.media.application.validation.AvatarMediaValidator;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.user.domain.event.UserFetchedEvent;
import com.blog.modules.user.domain.event.UserWasSubscribedEvent;
import com.blog.modules.user.domain.event.UserWasUnsubscribedEvent;
import com.blog.modules.user.domain.model.Notification;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.domain.port.out.SubscriptionRepository;
import com.blog.modules.user.domain.port.out.UserRepository;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.exception.EmailAlreadyExistsException;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.shared.infrastructure.exception.ConflictException;
import com.blog.shared.infrastructure.exception.ForbiddenException;
import com.blog.shared.infrastructure.exception.InternalServerErrorException;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MediaService mediaService;
    private final AvatarMediaValidator avatarMediaValidator;

    public UserServiceImpl(
            UserRepository userRepository,
            SubscriptionRepository subscriptionRepository,
            ApplicationEventPublisher eventPublisher,
            MediaService mediaService,
            AvatarMediaValidator avatarMediaValidator) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.eventPublisher = eventPublisher;
        this.mediaService = mediaService;
        this.avatarMediaValidator = avatarMediaValidator;
    }

    @Override
    public List<User> findAll(String query, Instant before, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        return userRepository.findAll(query, before, pageable);
    }

    @Override
    public List<User> getThreeActiveUsers() {

        Sort sort = Sort.by("postsCount").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);

        return userRepository.findAll(null, null, pageable);
    }

    @Override
    public User findById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return user;
    }

    @Override
    public Boolean userExist(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String getUserReadme(UUID currentUserId, UUID userId) {
        String readme = userRepository.getUserReadme(userId);
        if (!currentUserId.equals(userId)) { // prevent increment impressions if the user consulted his own profile
            eventPublisher.publishEvent(new UserFetchedEvent(userId));
        }
        return readme;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return user;
    }

    @Override
    public boolean isSubscribed(UUID currUserId, UUID targetUserId) {
        return subscriptionRepository.isSubscribed(currUserId, targetUserId);
    }

    @Override
    public Boolean isBanned(UUID userId) {
        return userRepository.isBanned(userId);
    }

    @Override
    public List<Notification> getNotifications(UUID userId, Instant before, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        return userRepository.getNotifications(userId, before, pageable);
    }

    @Override
    @Transactional
    public void createNotifications(UUID userId, UUID postId) {
        userRepository.createNotifications(userId, postId);
    }

    @Override
    @Transactional
    public void markNotifSeen(UUID userId, UUID notifId) {
        userRepository.markNotifSeen(userId, notifId);
    }

    // TODO: fix the avatar update
    @Override
    @Transactional
    public User updateUserInfo(UUID userId, UpdateUserCommand cmd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (cmd.name() != null) {
            user.changeName(cmd.name());
        }

        if (cmd.email() != null && !user.getEmail().equals(cmd.email())) {
            if (userRepository.findByEmail(cmd.email()).isPresent()) {
                throw new EmailAlreadyExistsException(cmd.email());
            }
            if (userRepository.isEmailVerified(userId)) {
                throw new ConflictException("You can't update verified email");
            }
            user.changeEmail(cmd.email());
        }

        if (cmd.avatar() != null) {
            avatarMediaValidator.validate(cmd.avatar());
            try {
                UUID mediaId = mediaService.uploadAvatar(userId, cmd.avatar());
                user.changeAvatar(mediaId);
            } catch (IOException | java.io.IOException | IllegalStateException e) {
                throw new InternalServerErrorException("Failed to upload avatar: " + e.getMessage());
            }
        }

        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public void changeUserStatus(UUID userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (userRepository.isAdmin(userId)) {
            if (userRepository.isSuperAdmin(userId)) {
                throw new ForbiddenException("You can't change the status of the supper user");
            }
        }

        user.changeStatus(status);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserRole(UUID userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (userRepository.isAdmin(userId)) {
            if (userRepository.isSuperAdmin(userId)) {
                throw new ForbiddenException("You can't change the role of the supper user");
            }
        }

        user.changeRole(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void subscribeToUser(UUID currUserId, UUID targetUserId) {
        if (currUserId.equals(targetUserId)) {
            throw new ConflictException("You can't subscribe to youself.");
        }
        if (subscriptionRepository.isSubscribed(currUserId, targetUserId)) {
            throw new ConflictException("You are already subscribed to this user");
        }

        subscriptionRepository.subscribe(currUserId, targetUserId);
        eventPublisher.publishEvent(new UserWasSubscribedEvent(targetUserId));
    }

    @Override
    @Transactional
    public void unsubscribeToUser(UUID currUserId, UUID targetUserId) {
        if (currUserId.equals(targetUserId)) {
            throw new ConflictException("You can't unsubscribe to youself.");
        }
        if (!subscriptionRepository.isSubscribed(currUserId, targetUserId)) {
            throw new ConflictException("You must be already subscribed to this user");
        }
        subscriptionRepository.unsubscribe(currUserId, targetUserId);
        eventPublisher.publishEvent(new UserWasUnsubscribedEvent(targetUserId));
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (userRepository.isAdmin(userId)) {
            if (userRepository.isSuperAdmin(userId)) {
                throw new ForbiddenException("You can't delete of the supper user");
            }
        }

        userRepository.deleteById(userId);
    }

    // counters
    @Override
    @Transactional
    public void incrementImpressionsCount(UUID userId) {
        userRepository.incrementImpressionsCount(userId);
    }

    @Override
    @Transactional
    public void incrementSubscriptionsCount(UUID userId) {
        userRepository.incrementSubscriptionsCount(userId);
    }

    @Override
    @Transactional
    public void decrementSubscriptionsCount(UUID userId) {
        userRepository.decrementSubscriptionsCount(userId);
    }

    @Override
    @Transactional
    public void incrementPostsCount(UUID userId) {
        userRepository.incrementPostsCount(userId);
    }

    @Override
    @Transactional
    public void decrementPostsCount(UUID userId) {
        userRepository.decrementPostsCount(userId);
    }

}
