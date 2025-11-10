package com.blog.modules.report.domain.port.out;

import java.util.UUID;

import com.blog.modules.report.domain.model.Report;

public interface ReportRepository {

    Report save(Report entity);

    void delete(UUID reportId);

}
