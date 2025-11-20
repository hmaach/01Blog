package com.blog.modules.report.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.report.domain.model.Report;
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

    @GetMapping
    public ResponseEntity<List<ReportResponse>> findAll(
            HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {

        List<Report> reports = reportService.findAll(before, size);
        List<ReportResponse> response = reports.stream().map(ReportResponse::fromDomain).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ReportResponse createReport(HttpServletRequest request, @Valid @RequestBody CreateReportCommand cmd) {
        UUID currentUserId = jwtService.extractUserIdFromRequest(request);

        return ReportResponse.fromDomain(reportService.createReport(currentUserId, cmd));
    }
}
