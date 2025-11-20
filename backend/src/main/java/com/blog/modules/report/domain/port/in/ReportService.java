package com.blog.modules.report.domain.port.in;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;

public interface ReportService {

    List<Report> findAll(Instant before, int size);

    Report createReport(UUID currentUserId, CreateReportCommand cmd);

}
