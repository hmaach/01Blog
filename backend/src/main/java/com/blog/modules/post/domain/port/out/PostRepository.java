package com.blog.modules.post.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.blog.modules.post.domain.model.Post;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(UUID id);

    List<Post> findByUserId(UUID id);

    List<Post> findAll();

    void attachMediaToPost(UUID postId, UUID mediaId);

    void incrementLikesCount(UUID postId);

    void deleteById(UUID id);

    void deletePostMediaLinks(UUID postId);

    void decrementLikesCount(UUID postId);

    void incrementImpressionsCount(List<UUID> postIds);

    void deleteMediaLinks(UUID mediaId);

    void hidePostById(UUID postId);
}
