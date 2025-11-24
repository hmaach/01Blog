package com.blog.modules.post.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.infrastructure.exception.MediaStorageException;
import com.blog.modules.post.domain.event.PostFetchedEvent;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.LikeService;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.post.infrastructure.exception.PostNotFoundException;
import com.blog.modules.user.domain.event.UserCreatedPostEvent;
import com.blog.modules.user.domain.event.UserDeletedPostEvent;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.shared.infrastructure.exception.ForbiddenException;
import com.blog.shared.infrastructure.exception.InternalServerErrorException;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final MediaService mediaService;
    private final LikeService likeService;
    private final ApplicationEventPublisher eventPublisher;

    public PostServiceImpl(
            PostRepository postRepository,
            UserService userService,
            MediaService mediaService,
            LikeService likeService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mediaService = mediaService;
        this.likeService = likeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Post> findAll(Instant before, int size) {

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        return postRepository.findAll(before, pageable);
    }

    @Override
    public List<Post> findFeedPosts(UUID currUserId, Instant before, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        return postRepository.findFeedPosts(currUserId, before, pageable);
    }

    @Override
    public List<Post> findByUserId(UUID id, Pageable pageable) {
        if (!userService.userExist(id)) {
            throw new UserNotFoundException(id.toString());
        }
        return postRepository.findByUserId(id, pageable);
    }

    @Override
    public List<Post> findByUserUsername(String username, Instant before, int size) {
        if (!userService.userExistByUsername(username)) {
            throw new UserNotFoundException(username);
        }

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);
        
        return postRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Post findById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        eventPublisher.publishEvent(new PostFetchedEvent(postId));
        System.err.println("---------------------  " + post.getUser().getName());
        return post;
    }

    @Override
    public Boolean existsById(UUID postId) {
        return postRepository.existsById(postId);
    }

    @Override
    @Transactional
    public Post createPost(CreatePostCommand cmd, UUID currentUserId) {
        UUID postId = UUID.randomUUID();

        Post post = new Post(
                postId,
                currentUserId,
                cmd.title(),
                cmd.body(),
                Instant.now()
        );

        postRepository.save(post);

        if (cmd.medias() != null && !cmd.medias().isEmpty()) {
            for (UUID mediaId : cmd.medias()) {
                postRepository.attachMediaToPost(postId, mediaId);
                mediaService.linkMediaToPost(mediaId);
            }
        }
        eventPublisher.publishEvent(new UserCreatedPostEvent(currentUserId));
        return post;
    }

    @Override
    @Transactional
    public Post updatePost(UUID postId, UpdatePostCommand cmd, UUID currentUserId, boolean isAdmin) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        if (!isAdmin && !currentUserId.equals(post.getUserId())) {
            throw new ForbiddenException();
        }

        boolean updated = false;

        if (cmd.getTitle() != null && !cmd.getTitle().equals(post.getTitle())) {
            post.updateTitle(cmd.getTitle());
            updated = true;
        }
        if (cmd.getBody() != null && !cmd.getBody().equals(post.getBody())) {
            post.updateBody(cmd.getBody());
            updated = true;
        }
        if (cmd.getAddedMedias() != null && !cmd.getAddedMedias().isEmpty()) {
            for (UUID mediaId : cmd.getAddedMedias()) {
                postRepository.attachMediaToPost(postId, mediaId);
                mediaService.linkMediaToPost(mediaId);
            }
        }

        if (cmd.getDeletedMedias() != null && !cmd.getDeletedMedias().isEmpty()) {
            if (cmd.getDeletedMedias() != null && !cmd.getDeletedMedias().isEmpty()) {
                for (UUID mediaId : cmd.getDeletedMedias()) {
                    try {
                        mediaService.deleteMediaFromPost(currentUserId, postId, mediaId);
                    } catch (IOException | java.io.IOException | IllegalStateException e) {
                        throw new InternalServerErrorException("Failed to delete media from post: " + e.getMessage());
                    }
                }
            }
        }

        if (updated) {
            postRepository.save(post);
        }

        return post;
    }

    @Override
    @Transactional
    public void likePost(UUID postId, UUID currentUserId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        likeService.likePost(postId, currentUserId);
    }

    @Override
    @Transactional
    public void incrementLikesCount(UUID postId) {
        postRepository.incrementLikesCount(postId);
    }

    @Override
    @Transactional
    public void decrementLikesCount(UUID postId) {
        postRepository.decrementLikesCount(postId);
    }

    @Override
    @Transactional
    public void incrementCommentsCount(UUID postId) {
        postRepository.incrementCommentsCount(postId);
    }

    @Override
    @Transactional
    public void decrementCommentsCount(UUID postId) {
        postRepository.decrementCommentsCount(postId);
    }

    @Override
    @Transactional
    public void incrementImpressionsCount(UUID postId) {
        postRepository.incrementImpressionsCount(postId);
    }

    @Override
    @Transactional
    public void deletePost(UUID postId, UUID currentUserId, boolean isAdmin) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        if (!isAdmin && !currentUserId.equals(post.getUserId())) {
            throw new ForbiddenException();
        }

        List<Media> mediaList = mediaService.findByPostId(post.getId());
        for (Media media : mediaList) {
            try {
                mediaService.deleteMedia(media);
            } catch (java.io.IOException e) {
                throw new MediaStorageException("Failed to delete media: " + e.getMessage());
            }
        }
        postRepository.deleteById(postId);
        eventPublisher.publishEvent(new UserDeletedPostEvent(currentUserId));
    }
}
