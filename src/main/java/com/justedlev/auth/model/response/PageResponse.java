package com.justedlev.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse<D> {
    private Integer page;
    private Integer maxPages;
    private D data;
}
