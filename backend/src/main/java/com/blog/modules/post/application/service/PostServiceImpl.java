package com.blog.modules.post.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.exception.MediaStorageException;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.post.domain.exception.PostNotFoundException;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.port.in.UserService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final MediaService mediaService;

    public PostServiceImpl(PostRepository postRepository, UserService userService, MediaService mediaService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mediaService = mediaService;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return post;
    }

    @Override
    public List<Post> findByUserId(UUID id) {
        if (userService.userExist(id).isEmpty()) {
            throw new UserNotFoundException(id.toString());
        }
        return postRepository.findByUserId(id);
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
                "published",
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
    public Post updatePost(UUID postId, UpdatePostCommand cmd, UUID currentUserId, boolean isAdmin) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException(postId.toString()));
        //     if (cmd.getName() != null) {
        //         user.changeName(cmd.getName());
        //     }
        //     if (cmd.getUsername() != null && !user.getUsername().equals(cmd.getUsername())) {
        //         if (userRepository.findByUsername(cmd.getUsername()).isPresent()) {
        //             throw new UsernameAlreadyExistsException(cmd.getUsername());
        //         }
        //         user.changeUsername(cmd.getUsername());
        //     }
        //     if (cmd.getEmail() != null && !user.getEmail().equals(cmd.getEmail())) {
        //         if (userRepository.findByEmail(cmd.getEmail()).isPresent()) {
        //             throw new EmailAlreadyExistsException(cmd.getEmail());
        //         }
        //         user.changeEmail(cmd.getEmail());
        //     }
        //     if (cmd.getPassword() != null) {
        //         user.changePassword(encoder.encode(cmd.getPassword()));
        //     }
        //     userRepository.save(user);
        return post;
    }

    @Override
    public void deletePost(UUID postId, UUID currentUserId, boolean isAdmin) {
        postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException(postId.toString()));
        postRepository.deleteById(postId);
    }

}
