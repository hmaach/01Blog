package com.blog.modules.user.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.modules.user.domain.event.UserFetchedEvent;
import com.blog.modules.user.domain.event.UserWasSubscribedEvent;
import com.blog.modules.user.domain.event.UserWasUnsubscribedEvent;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.domain.port.out.SubscriptionRepository;
import com.blog.modules.user.domain.port.out.UserRepository;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.exception.EmailAlreadyExistsException;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.modules.user.infrastructure.exception.UsernameAlreadyExistsException;
import com.blog.shared.infrastructure.exception.ConflictException;
import com.blog.shared.infrastructure.exception.ForbiddenException;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository,
            SubscriptionRepository subscriptionRepository,
            ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.eventPublisher = eventPublisher;
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
    @Transactional
    public User updateUser(UUID id, UpdateUserCommand cmd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        if (cmd.getName() != null) {
            user.changeName(cmd.getName());
        }

        if (cmd.getUsername() != null && !user.getUsername().equals(cmd.getUsername())) {
            if (userRepository.findByUsername(cmd.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(cmd.getUsername());
            }
            user.changeUsername(cmd.getUsername());
        }

        if (cmd.getEmail() != null && !user.getEmail().equals(cmd.getEmail())) {
            if (userRepository.findByEmail(cmd.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException(cmd.getEmail());
            }
            user.changeEmail(cmd.getEmail());
        }

        if (cmd.getPassword() != null) {
            user.changePassword(encoder.encode(cmd.getPassword()));
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
