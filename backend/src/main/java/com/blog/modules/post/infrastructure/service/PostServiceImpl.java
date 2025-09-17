package com.blog.modules.post.infrastructure.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreatePostCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdatePostCommand;
import com.blog.modules.post.infrastructure.adapter.out.persistence.PostRepositoryImpl;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepositoryImpl postRepository;
    private final UserRepositoryImpl userRepository;

    public PostServiceImpl(PostRepositoryImpl postRepository, UserRepositoryImpl userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Post> findByUserId(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

    @Override
    public Post createPost(CreatePostCommand command, UUID userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPost'");
    }

    @Override
    public Post updatePost(UUID postId, UpdatePostCommand cmd, UUID currentUserId, boolean isAdmin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
    }

    @Override
    public void deletePost(UUID postId, UUID currentUserId, boolean isAdmin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
    }

    // @Override
    // public Post createPost(CreatePostCommand cmd, String userId) {
    //     String id = UUID.randomUUID().toString();
    //     Post post = new Post(
    //             id,
    //             cmd.name(),
    //             cmd.description(),
    //             cmd.price(),
    //             userId,
    //             LocalDateTime.now()
    //     );
    //     postRepository.save(post);
    //     return post;
    // }
    // @Override
    // public Post findById(String id) {
    //     Post post = postRepository.findById(id)
    //             .orElseThrow(() -> new PostNotFoundException(id)
    //             );
    //     return post;
    // }
    // @Override
    // public List<Post> findByUserId(String id) {
    //     User user = userRepository.findById(id)
    //             .orElseThrow(() -> new UserNotFoundException(id));
    //     return postRepository.findByUserId(user.getId());
    // }
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // @Override
    // public Post updatePost(UUID postId, UpdatePostCommand cmd, String currentUserId, boolean isAdmin) {
    //     Post post = postRepository.findById(postId)
    //             .orElseThrow(() -> new PostNotFoundException(postId));
    //     if (!isAdmin && !post.getUserId().equals(currentUserId)) {
    //         throw new AccessDeniedException("You cannot update this post");
    //     }
    //     if (cmd.getName() != null) {
    //         post.updateName(cmd.getName());
    //     }
    //     if (cmd.getDescription() != null) {
    //         post.updateDescription(cmd.getDescription());
    //     }
    //     if (cmd.getPrice() != null) {
    //         post.updatePrice(cmd.getPrice());
    //     }
    //     postRepository.save(post);
    //     return post;
    // }
    // @Override
    // public void deletePost(String postId, String currentUserId, boolean isAdmin) {
    //     Post post = postRepository.findById(postId)
    //             .orElseThrow(() -> new PostNotFoundException(postId));
    //     if (!isAdmin && !post.getUserId().equals(currentUserId)) {
    //         throw new AccessDeniedException("You cannot delete this post");
    //     }
    //     postRepository.deleteById(postId);
    // }
}
