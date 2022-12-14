package com.justedlev.auth.constant;

public final class RoleTypeConstant {
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ADMIN = "ADMIN";
    public static final String SUPER_USER = "SUPER_USER";
    public static final String USER = "USER";

    private RoleTypeConstant() {
        throw new IllegalStateException("Constants class");
    }
}
