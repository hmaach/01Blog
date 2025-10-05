package com.blog.modules.post.application.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.modules.post.domain.model.Comment;
import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.out.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository
    ) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
