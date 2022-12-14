package com.justedlev.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryRequest {
    private Set<String> emails;
    private Set<String> usernames;
    private PaginationRequest pageRequest;
}
