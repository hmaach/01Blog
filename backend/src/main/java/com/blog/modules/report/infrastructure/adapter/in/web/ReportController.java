package com.blog.modules.report.infrastructure.adapter.in.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.CreateReportCommand;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.ReportResponse;
import com.blog.shared.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final JwtService jwtService;

    public ReportController(ReportService reportService, JwtService jwtService) {
        this.reportService = reportService;
        this.jwtService = jwtService;
    }



    @PostMapping()
    public ReportResponse createReport(HttpServletRequest request, @Valid @RequestBody CreateReportCommand cmd) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);

        return ReportResponse.fromDomain(reportService.createReport(currentUserId, cmd));
    }
}
