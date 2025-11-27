package com.blog.modules.report.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Report> findAll(Instant before, Pageable pageable) {
        Page<ReportEntity> reports;

        if (before == null && pageable != null) {
            reports = jpaRepository.findAll(pageable);
        } else {
            reports = jpaRepository.findFeedReportsBefore(before, pageable);
        }

        return reports.stream()
                .map(ReportMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Report save(Report report) {
        ReportEntity entity = ReportMapper.toEntity(report);
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return ReportMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean existsById(UUID reportId) {
        if (reportId == null) {
            return false;
        }
        return jpaRepository.existsById(reportId);
    }

    @Override
    public void changeStatus(UUID reportId, String status) {
        jpaRepository.changeStatus(reportId, status);
    }

    @Override
    public void delete(UUID reportId) {
        if (reportId != null) {
            jpaRepository.deleteById(reportId);
        }
    }

}
