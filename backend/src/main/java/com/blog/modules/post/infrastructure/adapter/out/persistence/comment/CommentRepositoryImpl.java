package com.blog.modules.post.infrastructure.adapter.out.persistence.comment;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.out.CommentRepository;

import jakarta.persistence.EntityManager;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SpringDataCommentRepository jpaRepository;
    private final EntityManager entityManager;

    public CommentRepositoryImpl(SpringDataCommentRepository jpaRepository, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .stream()
                .map(CommentMapper::toDomain)
                .toList();
    }

}
