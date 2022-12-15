package com.justedlev.auth.repository.custom.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginJournalFilter {
    private Set<String> userId;
    private Timestamp fromLoginDate;
    private Timestamp toLoginDate;
}
