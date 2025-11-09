package com.blog.modules.report.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.report.domain.port.out.ReportRepository;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;
import com.blog.modules.report.infrastructure.adapter.out.persistence.ReportEntity;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(UUID currentUserId, CreateReportCommand cmd) {
        ReportEntity entity = new ReportEntity();
        entity.setReporterId(currentUserId);
        entity.setCategory(cmd.category());
        entity.setReason(cmd.reason());
        entity.setCreatedAt(Instant.now());
        entity.setReportedPostId(cmd.reportedPostId());
        entity.setReportedCommentId(cmd.reportedCommentId());

        return reportRepository.save(entity);
    }
}
