package com.blog.modules.post.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.blog.modules.post.domain.model.Post;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(String id);

    List<Post> findByUserId(String id);

    List<Post> findAll();

    void deleteById(String id);
}
