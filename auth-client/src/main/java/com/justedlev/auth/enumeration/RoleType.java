package com.justedlev.auth.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    SUPER_ADMIN("SUPER_ADMIN", "ADMINISTRATORS"),
    ADMIN("ADMIN", "ADMINISTRATORS"),
    SUPER_USER("SUPER_USER", "USERS"),
    USER("USER", "USERS");
    private final String type;
    private final String group;
}
