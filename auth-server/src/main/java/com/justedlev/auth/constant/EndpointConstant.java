package com.justedlev.auth.constant;

public final class EndpointConstant {
    public static final String V1 = "/v1";
    public static final String PAGE = "/page";
    public static final String ADD = "/add";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String DEACTIVATE = "/deactivate";
    public static final String ACTIVATE = "/activate";
    public static final String CONFIRM = "/confirm";
    public static final String PHOTO = "/photo";
    public static final String OFFLINE = "/offline";
    public static final String SLEEP = "/sleep";

    // User
    public static final String USER = V1 + "/user";
    public static final String USERNAME = "/" + PathVariableConstant.USERNAME;
    public static final String USERNAME_DELETE = USERNAME + DELETE;
    public static final String USER_USERNAME = USER + USERNAME;
    public static final String USER_USERNAME_DELETE = USER + USERNAME_DELETE;

    // Account
    public static final String ACCOUNT = V1 + "/account";
    public static final String ACCOUNT_SLEEP = ACCOUNT + SLEEP;
    public static final String ACCOUNT_OFFLINE = ACCOUNT + OFFLINE;
    public static final String NICKNAME = "/" + PathVariableConstant.NICKNAME;
    public static final String EMAIL = "/" + PathVariableConstant.EMAIL;
    public static final String ACCOUNT_NICKNAME = ACCOUNT + NICKNAME;
    public static final String NICKNAME_UPDATE = NICKNAME + UPDATE;
    public static final String ACCOUNT_NICKNAME_UPDATE = ACCOUNT + NICKNAME_UPDATE;
    public static final String NICKNAME_UPDATE_PHOTO = NICKNAME_UPDATE + PHOTO;
    public static final String ACCOUNT_NICKNAME_UPDATE_PHOTO = ACCOUNT + NICKNAME_UPDATE_PHOTO;
    public static final String NICKNAME_DEACTIVATE = NICKNAME + DEACTIVATE;
    public static final String ACCOUNT_NICKNAME_DEACTIVATE = ACCOUNT + NICKNAME_DEACTIVATE;
    public static final String NICKNAME_ACTIVATE = NICKNAME + ACTIVATE;
    public static final String ACCOUNT_NICKNAME_ACTIVATE = NICKNAME + NICKNAME_ACTIVATE;
    public static final String NICKNAME_DELETE = NICKNAME + DELETE;
    public static final String ACCOUNT_NICKNAME_DELETE = ACCOUNT + NICKNAME_DELETE;
    public static final String ACCOUNT_CONFIRM = ACCOUNT + CONFIRM;
    public static final String CODE = "/" + PathVariableConstant.CODE;
    public static final String CONFIRM_CODE = CONFIRM + CODE;
    public static final String ACCOUNT_CONFIRM_CODE = ACCOUNT_CONFIRM + CODE;

    // Auth
    public static final String AUTH = V1 + "/auth";
    public static final String SIGNUP = "/signup";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = AUTH + "/logout";
    public static final String LOGIN_REFRESH = LOGIN + REFRESH;
    public static final String AUTH_SIGNUP = AUTH + SIGNUP;
    public static final String AUTH_LOGIN = AUTH + LOGIN;
    public static final String AUTH_LOGIN_REFRESH = AUTH + LOGIN_REFRESH;

    // History
    public static final String HISTORY = V1 + "/history";
    public static final String HISTORY_ACCOUNT = HISTORY + ACCOUNT;
    public static final String HISTORY_USER = HISTORY + USER;

    // Role
    public static final String ROLE = V1 + "/role";
    public static final String ROLE_TYPE = "/" + PathVariableConstant.ROLE_TYPE;
    public static final String ROLE_TYPE_UPDATE = ROLE_TYPE + UPDATE;
    public static final String ROLE_TYPE_DELETE = ROLE_TYPE + DELETE;
    public static final String ROLE_ROLE_TYPE = ROLE + ROLE_TYPE;
    public static final String ROLE_ROLE_TYPE_DELETE = ROLE + ROLE_TYPE_DELETE;
    public static final String ROLE_ROLE_TYPE_UPDATE = ROLE + ROLE_TYPE_UPDATE;

    private EndpointConstant() {
        throw new IllegalStateException("Constants class");
    }
}
