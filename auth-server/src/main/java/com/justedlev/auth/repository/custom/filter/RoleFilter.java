package com.justedlev.auth.repository.custom.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleFilter {
    private Set<String> ids;
    private Set<String> types;
    private Set<String> groups;
    private String about;
}
