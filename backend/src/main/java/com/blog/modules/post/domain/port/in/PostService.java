package com.blog.modules.post.domain.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;

public interface PostService {

    List<Post> findAll(Pageable pageable);

    List<Post> findByUserId(UUID id, Pageable pageable);

    Post findById(UUID id);

    Boolean existsById(UUID id);

    Post createPost(CreatePostCommand command, UUID userID);

    Post updatePost(UUID postId, UpdatePostCommand cmd, UUID currentUserId, boolean isAdmin);

    void likePost(UUID postId, UUID currentUserId);

    void deletePost(UUID postId, UUID currentUserId, boolean isAdmin);

    void incrementLikesCount(UUID postId);

    void decrementLikesCount(UUID postId);

    void incrementImpressionsCount(List<UUID> postIds);

    List<Post> findByUserUsername(String username, Pageable pageable);
}
