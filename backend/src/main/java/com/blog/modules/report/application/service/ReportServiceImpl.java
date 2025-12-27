package com.blog.modules.report.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.modules.post.domain.port.in.CommentService;
import com.blog.modules.post.domain.port.in.PostService;
import com.blog.modules.post.infrastructure.exception.CommentNotFoundException;
import com.blog.modules.post.infrastructure.exception.PostNotFoundException;
import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.report.domain.port.out.ReportRepository;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;
import com.blog.modules.report.infrastructure.exception.ReportNotFoundException;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.shared.infrastructure.exception.ConflictException;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public ReportServiceImpl(
            ReportRepository reportRepository,
            UserService userService,
            PostService postService,
            CommentService commentService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public List<Report> findAll(Instant before, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        return reportRepository.findAll(before, pageable);
    }

    @Override
    public List<Report> getLastThreeReports() {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);

        return reportRepository.findAll(null, pageable);
    }

    @Override
    @Transactional
    public Report createReport(UUID currentUserId, CreateReportCommand cmd) {
        if (currentUserId.equals(cmd.reportedUserId())) {
            throw new ConflictException("You can't report yourself.");
        }
        if (!userService.userExist(cmd.reportedUserId())) {
            throw new UserNotFoundException(cmd.reportedUserId().toString());
        }

        if (cmd.reportedType().equals("POST")) {
            if (cmd.reportedPostId() == null) {
                throw new ConflictException("Post's id must be provided");
            }
            if (!postService.existsById(cmd.reportedPostId())) {
                throw new PostNotFoundException(cmd.reportedPostId().toString());
            }
        } else if (cmd.reportedType().equals("COMMENT")) {
            if (cmd.reportedCommentId() == null) {
                throw new ConflictException("Comment's id must be provided");
            }
            if (!commentService.existsById(cmd.reportedCommentId())) {
                throw new CommentNotFoundException(cmd.reportedCommentId().toString());
            }
        }

        Report report = new Report();

        report.setReporterId(currentUserId);
        report.setReportedType(cmd.reportedType());
        report.setReportedUserId(cmd.reportedUserId());
        report.setReportedPostId(cmd.reportedPostId());
        report.setReportedCommentId(cmd.reportedCommentId());
        report.setCategory(cmd.category());
        report.setReason(cmd.reason());
        report.setStatus("PENDING");
        report.setCreatedAt(Instant.now());

        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public void changeStatus(UUID reportId, String status) {
        if (!reportRepository.existsById(reportId)) {
            throw new ReportNotFoundException(reportId.toString());
        }

        reportRepository.changeStatus(reportId, status);
    }

    @Override
    @Transactional
    public void deleteReport(UUID reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ReportNotFoundException(reportId.toString());
        }

        reportRepository.delete(reportId);
    }
}
