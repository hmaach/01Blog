package com.blog.modules.post.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.exception.MediaStorageException;
import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.post.domain.event.PostFetchedEvent;
import com.blog.modules.post.domain.event.PostsFetchedEvent;
import com.blog.modules.post.domain.exception.PostNotFoundException;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.in.LikeService;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.shared.infrastructure.exception.ForbiddenException;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final MediaService mediaService;
    private CommentService commentService;
    private LikeService likeService;
    private final ApplicationEventPublisher eventPublisher;

    public PostServiceImpl(
            PostRepository postRepository,
            UserService userService,
            MediaService mediaService,
            CommentService commentService,
            LikeService likeService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mediaService = mediaService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Post> findAll(Pageable pageable) {
        List<Post> posts = postRepository.findAll(pageable);
        eventPublisher.publishEvent(new PostsFetchedEvent(posts));
        return posts;
    }

    @Override
    public List<Post> findByUserId(UUID id, Pageable pageable) {
        if (!userService.userExist(id)) {
            throw new UserNotFoundException(id.toString());
        }
        return postRepository.findByUserId(id, pageable);
    }

    @Override
    public List<Post> findByUserUsername(String username, Pageable pageable) {
        if (!userService.userExistByUsername(username)) {
            throw new UserNotFoundException(username);
        }
        return postRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Post findById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        eventPublisher.publishEvent(new PostFetchedEvent(postId));
        return post;
    }

    @Override
    public Boolean existsById(UUID postId) {
        return postRepository.existsById(postId);
    }

    @Override
    @Transactional
    public Post createPost(CreatePostCommand cmd, UUID userId) {
        UUID postId = UUID.randomUUID();

        Post post = new Post(
                postId,
                userId,
                cmd.title(),
                cmd.body(),
                Instant.now()
        );

        postRepository.save(post);

        if (cmd.files() != null && !cmd.files().isEmpty()) {
            for (MultipartFile file : cmd.files()) {
                try {
                    mediaService.savePostMedia(userId, postId, file);
                } catch (java.io.IOException e) {
                    throw new MediaStorageException("Failed to store media: " + e.getMessage());
                }
            }
        }

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
    public void incrementImpressionsCount(List<UUID> postIds) {
        postRepository.incrementImpressionsCount(postIds);
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
    }
}
