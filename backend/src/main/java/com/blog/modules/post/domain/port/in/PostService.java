package com.blog.modules.post.domain.port.in;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;

public interface PostService {

    List<Post> findAll(Instant before, int size);

    List<Post> findFeedPosts(UUID currUserId, Instant before, int size);

    List<Post> findByUserUsername(String username, Instant before, int size);

    List<Post> findByUserId(UUID id, Pageable pageable);

    Post findById(UUID id);

    Boolean existsById(UUID id);

    Post createPost(CreatePostCommand command, UUID userID);

    Post updatePost(UUID postId, UpdatePostCommand cmd, UUID currentUserId, boolean isAdmin);

    void likePost(UUID postId, UUID currentUserId);

    void deletePost(UUID postId, UUID currentUserId, boolean isAdmin);

    void incrementLikesCount(UUID postId);

    void decrementLikesCount(UUID postId);

    void incrementImpressionsCount(UUID postIds);

    void incrementCommentsCount(UUID postId);

    void decrementCommentsCount(UUID postId);

}
