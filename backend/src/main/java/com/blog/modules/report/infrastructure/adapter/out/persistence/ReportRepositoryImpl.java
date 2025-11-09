package com.blog.modules.report.infrastructure.adapter.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.report.domain.model.Report;
import com.blog.modules.report.domain.port.out.ReportRepository;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final SpringDataReportRepository jpaRepository;

    public ReportRepositoryImpl(SpringDataReportRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Report save(ReportEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return ReportMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void delete(UUID reportId) {
        if (reportId != null) {
            jpaRepository.deleteById(reportId);
        }
    }

}
