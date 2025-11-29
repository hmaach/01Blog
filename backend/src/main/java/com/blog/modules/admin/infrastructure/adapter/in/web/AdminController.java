package com.blog.modules.admin.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.modules.admin.domain.model.AdminStats;
import com.blog.modules.admin.domain.port.in.AdminService;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.AdminStatsResponse;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.ChangeReportStatusCommand;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.ChangeUserRoleCommand;
import com.blog.modules.admin.infrastructure.adapter.in.web.dto.ChangeUserStatusCommand;
import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.domain.port.in.ReportService;
import com.blog.modules.report.infrastructure.adapter.in.web.dto.ReportResponse;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final ReportService reportService;

    public AdminController(
            AdminService adminService,
            UserService userService,
            ReportService reportService
    ) {
        this.adminService = adminService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsResponse> getAdminStats() {
        AdminStats stats = adminService.getAdminStats();
        List<User> users = userService.getThreeActiveUsers();
        List<Report> reports = reportService.getLastThreeReports();

        return ResponseEntity.ok(AdminStatsResponse.fromDomain(stats, users, reports));
    }

    // =================== users Endpoints handlers   =================== 
    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable UUID userId) {
        return UserResponse.fromDomain(userService.findById(userId));
    }

    @GetMapping("/users")
    public List<UserProfileResponse> getUsers(
            HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.findAll(before, size).stream()
                .map(UserProfileResponse::fromDomain)
                .toList();
    }

    @PostMapping("/users/change-role/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chnageUserRole(
            @PathVariable UUID userId,
            @Valid @RequestBody ChangeUserRoleCommand cmd
    ) {
        userService.changeUserRole(userId, cmd.role());
    }

    @PostMapping("/users/change-status/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chnageUserStatus(
            @PathVariable UUID userId,
            @Valid @RequestBody ChangeUserStatusCommand cmd
    ) {
        userService.changeUserStatus(userId, cmd.status());
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

    // =================== reports Endpoints handlers   =================== 
    @GetMapping("/reports")
    public ResponseEntity<List<ReportResponse>> findAll(
            HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant before,
            @RequestParam(defaultValue = "10") int size
    ) {

        List<Report> reports = reportService.findAll(before, size);
        List<ReportResponse> response = reports.stream().map(ReportResponse::fromDomain).toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reports/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeReportStatus(@PathVariable UUID reportId, @Valid @RequestBody ChangeReportStatusCommand cmd) {
        reportService.changeStatus(reportId, cmd.status());
    }

    @DeleteMapping("/reports/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReportStatus(@PathVariable UUID reportId) {
        reportService.deleteReport(reportId);
    }

}
