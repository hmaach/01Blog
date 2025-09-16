package com.blog.modules.post.domain.port.in;

import java.util.List;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;

public interface PostService {

    List<Post> findAll();

    Post findById(String id);

    List<Post> findByUserId(String id);

    Post createPost(CreatePostCommand command, String userID);

    Post updatePost(String postId, UpdatePostCommand cmd, String currentUserId, boolean isAdmin);

    void deletePost(String postId, String currentUserId, boolean isAdmin);
}
