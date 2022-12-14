package com.justedlev.auth.common.mapper;

import com.justedlev.auth.model.response.ReportResponse;

public interface ReportMapper {
    ReportResponse toReport(String message, String details);

    ReportResponse toReport(String message);

    ReportResponse toReport();
}
