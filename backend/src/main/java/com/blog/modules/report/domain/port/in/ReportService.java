package com.blog.modules.report.domain.port.in;

import java.util.UUID;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;

public interface ReportService {

    Report createReport(UUID currentUserId, CreateReportCommand cmd);
    
}
