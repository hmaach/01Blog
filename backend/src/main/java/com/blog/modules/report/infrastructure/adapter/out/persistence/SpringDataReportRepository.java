package com.blog.modules.report.infrastructure.adapter.out.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReportRepository extends JpaRepository<ReportEntity, UUID> {

}
