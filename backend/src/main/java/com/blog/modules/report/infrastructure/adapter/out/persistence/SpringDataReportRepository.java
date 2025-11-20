package com.blog.modules.report.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataReportRepository extends JpaRepository<ReportEntity, UUID> {

    @Query("""
            SELECT r
            FROM ReportEntity r
            WHERE r.createdAt < :before
            ORDER BY r.createdAt DESC
        """)
    Page<ReportEntity> findFeedReportsBefore(Instant before, Pageable pageable);

}
