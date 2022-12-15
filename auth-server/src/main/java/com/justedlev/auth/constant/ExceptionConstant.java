package com.justedlev.auth.constant;

public final class ExceptionConstant {
    public static final String USER_NOT_EXISTS = "User '%s' not exists";
    public static final String USER_EXISTS = "User '%s' already exists";
    public static final String NICKNAME_TAKEN = "Nickname '%s' already taken";
    public static final String NOT_FOUND = "Not found";
    public static final String ROLE_NOT_EXISTS = "Role '%s' not exists";
    public static final String USER_ALREADY_DELETED = "User '%s' already deleted";
    public static final String ACCOUNT_ALREADY_DELETED = "Account '%s' already deleted";

    private ExceptionConstant() {
        throw new IllegalStateException("Utility class");
    }
}
