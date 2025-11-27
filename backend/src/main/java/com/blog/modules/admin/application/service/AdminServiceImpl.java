package com.blog.modules.admin.application.service;

import org.springframework.stereotype.Service;

import com.blog.modules.admin.domain.model.AdminStats;
import com.blog.modules.admin.domain.port.in.AdminService;
import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.user.domain.port.in.UserService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final ReportService reportService;

    public AdminServiceImpl(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @Override
    public AdminStats getAdminStats() {
        return new AdminStats(
                54,
                54,
                4,
                8,
                87,
                8
        );
    }

}
