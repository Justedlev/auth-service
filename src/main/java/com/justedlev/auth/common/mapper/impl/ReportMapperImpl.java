package com.justedlev.auth.common.mapper.impl;

import com.justedlev.auth.common.mapper.ReportMapper;
import com.justedlev.auth.model.response.ReportResponse;
import org.springframework.stereotype.Component;

@Component
public class ReportMapperImpl implements ReportMapper {
    public static final String SUCCESSFULLY = "Successfully!";
    public static final String FAIL = "Fail";

    @Override
    public ReportResponse toReport(String message, String details) {
        return ReportResponse.builder()
                .message(message)
                .details(details)
                .build();
    }

    @Override
    public ReportResponse toReport(String message) {
        return ReportResponse.builder()
                .message(message)
                .build();
    }

    @Override
    public ReportResponse toReport() {
        return toReport(SUCCESSFULLY);
    }
}
