package com.blog.modules.admin.infrastructure.adapter.in.web.dto;

import java.util.List;

import com.blog.modules.admin.domain.model.AdminStats;
import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.ReportResponse;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserProfileResponse;

public record AdminStatsResponse(
        Integer totalUsers,
        Integer totalPosts,
        Integer totalReports,
        Integer totalUserReports,
        Integer totalPostReports,
        Integer totalCommentReports,
        List<UserProfileResponse> threeActiveUsers,
        List<ReportResponse> lastThreeReports
        ) {

    public static AdminStatsResponse fromDomain(AdminStats stats, List<User> users, List<Report> reports) {
        return new AdminStatsResponse(
                stats.getTotalUsers(),
                stats.getTotalPosts(),
                stats.getTotalReports(),
                stats.getTotalUserReports(),
                stats.getTotalPostReports(),
                stats.getTotalCommentReports(),
                users.stream().map(UserProfileResponse::fromDomain).toList(),
                reports.stream().map(ReportResponse::fromDomain).toList()
        );
    }
}
