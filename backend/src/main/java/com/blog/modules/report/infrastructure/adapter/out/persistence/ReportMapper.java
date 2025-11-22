package com.blog.modules.report.infrastructure.adapter.out.persistence;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.user.infrastructure.adapter.out.persistence.user.UserMapper;

public class ReportMapper {

    public static ReportEntity toEntity(Report report) {
        return new ReportEntity(
                report.getId(),
                report.getReporterId(),
                report.getReportedType(),
                report.getReportedUserId(),
                report.getReportedPostId(),
                report.getReportedCommentId(),
                report.getCategory(),
                report.getReason(),
                report.getStatus(),
                report.getCreatedAt()
        );
    }

    public static Report toDomain(ReportEntity entity) {
        return new Report(
                entity.getId(),
                UserMapper.toDomain(entity.getReporterUser()),
                UserMapper.toDomain(entity.getReportedUser()),
                entity.getReportedType(),
                entity.getReportedPostId(),
                entity.getReportedCommentId(),
                entity.getCategory(),
                entity.getReason(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
