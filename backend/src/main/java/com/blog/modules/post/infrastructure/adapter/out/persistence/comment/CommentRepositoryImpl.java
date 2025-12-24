package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.out.CommentRepository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SpringDataCommentRepository jpaRepository;

    public CommentRepositoryImpl(SpringDataCommentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Comment> findByPostId(UUID postId, Instant before, Pageable pageable) {

        Page<CommentEntity> comments;
        if (before == null) {
            comments = jpaRepository.findByPostId(postId, pageable);
        } else {
            comments = jpaRepository.findByPostIdBefore(postId, before, pageable);
        }

        return comments.stream()
                .map(CommentMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(UUID commentId) {
        if (commentId == null) {
            return false;
        }
        return jpaRepository.existsById(commentId);
    }

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = CommentMapper.toEntity(comment);
        return CommentMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Comment> findById(UUID commentId) {
        if (commentId == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(commentId).map(CommentMapper::toDomain);
    }

    @Override
    public void deleteById(UUID commentId) {
        if (commentId != null) {
            jpaRepository.deleteById(commentId);
        }
    }
}
