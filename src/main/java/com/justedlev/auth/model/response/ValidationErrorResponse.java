package com.justedlev.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ValidationErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Builder.Default
    private Date timestamp = new Date();
    @Builder.Default
    private List<Violation> violations = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Violation {
        private String fieldName;
        private String message;
    }
}
