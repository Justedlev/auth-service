package com.justedlev.auth.component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.justedlev.auth.model.response.TokenResponse;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface TokenComponent {
    DecodedJWT decode(String token);

    String getSubject(String token);

    TokenResponse generateToken(UserEntity user, Account account);

    String getToken(HttpServletRequest request);

    DecodedJWT verifyAccessToken(String token);

    DecodedJWT verifyRefreshToken(String token);
}
