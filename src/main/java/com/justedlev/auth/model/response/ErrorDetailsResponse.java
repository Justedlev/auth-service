package com.justedlev.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorDetailsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Builder.Default
    private Date timestamp = new Date();
    private String message;
    private String details;
}
