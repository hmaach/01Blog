package com.blog.modules.report.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.report.domain.port.out.ReportRepository;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.exception.UserNotFoundException;
import com.blog.shared.infrastructure.exception.ConflictException;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;

    public ReportServiceImpl(ReportRepository reportRepository, UserService userService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
    }

    @Override
    public Report createReport(UUID currentUserId, CreateReportCommand cmd) {
        if (currentUserId.equals(cmd.reportedUserId())) {
            throw new ConflictException("You can't report yourself.");
        }
        if (!userService.userExist(cmd.reportedUserId())) {
            throw new UserNotFoundException(cmd.reportedUserId().toString());
        }

        Report report = new Report();

        report.setReporterId(currentUserId);
        report.setReportedUserId(cmd.reportedUserId());
        report.setReportedPostId(cmd.reportedPostId());
        report.setReportedCommentId(cmd.reportedCommentId());
        report.setCategory(cmd.category());
        report.setReason(cmd.reason());
        report.setStatus("pending");
        report.setCreatedAt(Instant.now());

        return reportRepository.save(report);
    }
}
