package com.blog.modules.post.application.service;

import org.springframework.stereotype.Service;

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
}
