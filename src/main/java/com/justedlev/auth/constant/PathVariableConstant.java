package com.justedlev.auth.constant;

public final class PathVariableConstant {
    public static final String NICKNAME = "{nickname}";
    public static final String CODE = "{code}";
    public static final String EMAIL = "{email}";
    public static final String ROLE_TYPE = "{type}";
    public static final String USERNAME = "{username}";

    private PathVariableConstant() {
        throw new IllegalStateException("Constants class");
    }
}
