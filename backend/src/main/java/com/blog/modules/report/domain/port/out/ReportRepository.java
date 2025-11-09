package com.blog.modules.report.domain.port.out;

import java.util.UUID;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.infrastructure.adapter.out.persistence.ReportEntity;

public interface ReportRepository {

    Report save(ReportEntity entity);

    void delete(UUID reportId);

}
