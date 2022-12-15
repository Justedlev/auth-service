package com.justedlev.auth.service;

import com.justedlev.auth.model.request.LoginRefreshRequest;
import com.justedlev.auth.model.request.LoginRequest;
import com.justedlev.auth.model.request.RegistrationRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.TokenResponse;

public interface AuthService {
    ReportResponse signup(RegistrationRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse loginRefresh(LoginRefreshRequest request);

    ReportResponse logout(String nickname);
}
