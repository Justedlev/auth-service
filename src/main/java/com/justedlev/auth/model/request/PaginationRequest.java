package com.justedlev.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationRequest {
    @Min(value = 1, message = "Page number cannot be less then 1.")
    @NotNull(message = "Page number cannot be null.")
    private Integer page;
    @Min(value = 1, message = "Page size cannot be less then 1.")
    @NotNull(message = "Page size cannot be null.")
    private Integer size;
}
