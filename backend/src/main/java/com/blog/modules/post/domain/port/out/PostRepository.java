package com.blog.modules.post.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.post.domain.model.Post;

public interface PostRepository {

    List<Post> findAll(Pageable pageable);

    List<Post> findByUserId(UUID id, Pageable pageable);

    Optional<Post> findById(UUID id);

    Post save(Post post);

    void attachMediaToPost(UUID postId, UUID mediaId);

    void incrementLikesCount(UUID postId);

    void deleteById(UUID id);

    void deletePostMediaLinks(UUID postId);

    void decrementLikesCount(UUID postId);

    void incrementImpressionsCount(List<UUID> postIds);

    void deleteMediaLinks(UUID mediaId);

    void hidePostById(UUID postId);
}
