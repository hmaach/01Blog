package com.blog.modules.report.infrastructure.exception;

import org.springframework.http.HttpStatus;

import com.blog.shared.infrastructure.exception.BaseException;

public class ReportNotFoundException extends BaseException {

    public ReportNotFoundException(String id) {
        super("REPORT_NOT_FOUND", "Report doesn't exist: " + id, HttpStatus.NOT_FOUND);
    }
}
