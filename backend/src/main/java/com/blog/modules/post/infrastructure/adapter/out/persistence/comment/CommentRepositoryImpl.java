package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.out.CommentRepository;

import jakarta.persistence.EntityManager;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SpringDataCommentRepository jpaRepository;
    @SuppressWarnings("unused")
    private final EntityManager entityManager;

    public CommentRepositoryImpl(SpringDataCommentRepository jpaRepository, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Comment> findByPostId(UUID postId, Pageable pageable) {
        return jpaRepository.findByPostId(postId, pageable)
                .stream()
                .map(CommentMapper::toDomain)
                .toList();
    }

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = CommentMapper.toEntity(comment);
        return CommentMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Comment> findById(UUID commentId) {
        return jpaRepository.findById(commentId).map(CommentMapper::toDomain);
    }

    @Override
    public void deleteById(UUID commentId) {
        jpaRepository.deleteById(commentId);
    }
}
