package com.blog.modules.report.domain.port.out;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.blog.modules.report.domain.model.Report;

public interface ReportRepository {

    List<Report> findAll(Instant before, Pageable pageable);

    Report save(Report entity);

    void delete(UUID reportId);

    boolean existsById(UUID reportId);

    void changeStatus(UUID reportId, String status);

}
