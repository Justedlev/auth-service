package com.justedlev.auth.component.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.justedlev.auth.properties.ServiceProperties;
import com.justedlev.auth.properties.TokenAccessProperties;
import com.justedlev.auth.properties.TokenRefreshProperties;
import com.justedlev.auth.model.response.TokenResponse;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.Role;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.component.TokenComponent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenComponentImpl implements TokenComponent {
    private final TokenAccessProperties tokenAccessProperties;
    private final TokenRefreshProperties tokenRefreshProperties;
    private final ServiceProperties serviceProperties;
    private static final String TYPE_CLAIM = "type";
    private static final String ROLES_CLAIM = "roles";
    private static final String EMAIL_CLAIM = "email";

    @Override
    public DecodedJWT decode(String token) {
        return Optional.ofNullable(token)
                .filter(StringUtils::isNotBlank)
                .map(JWT::decode)
                .orElse(null);
    }

    @Override
    public String getSubject(String token) {
        return Optional.ofNullable(decode(token))
                .map(DecodedJWT::getSubject)
                .orElse(null);
    }

    @Override
    public TokenResponse generateToken(UserEntity user, Account account) {
        var accessTokenExpiresIn = new Date(System.currentTimeMillis() + tokenAccessProperties.getExpirationTime().toMillis());
        var refreshTokenExpiresIn = new Date(System.currentTimeMillis() + tokenRefreshProperties.getExpirationTime().toMillis());
        var accessToken = generateAccessToken(account, user, accessTokenExpiresIn);
        var refreshToken = generateRefreshToken(account, user, refreshTokenExpiresIn);

        return TokenResponse.builder()
                .tokenType(tokenAccessProperties.getType())
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }

    @Override
    public String getToken(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(current -> current.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(StringUtils::isNotBlank)
                .filter(current -> current.startsWith(tokenAccessProperties.getType()))
                .map(current -> current.split(tokenAccessProperties.getType()))
                .filter(ArrayUtils::isNotEmpty)
                .flatMap(current -> Arrays.stream(current)
                        .filter(StringUtils::isNotBlank)
                        .map(String::trim)
                        .map(String::strip)
                        .findFirst())
                .orElse(null);
    }

    @Override
    public DecodedJWT verifyAccessToken(String token) {
        return JWT.require(Algorithm.HMAC256(tokenAccessProperties.getSecret().getBytes()))
                .withIssuer(serviceProperties.getHost())
                .build()
                .verify(token);
    }

    @Override
    public DecodedJWT verifyRefreshToken(String token) {
        return JWT.require(Algorithm.HMAC256(tokenRefreshProperties.getSecret().getBytes()))
                .withIssuer(serviceProperties.getHost())
                .withClaim(TYPE_CLAIM, tokenRefreshProperties.getType())
                .build()
                .verify(token);
    }

    private String generateRefreshToken(Account account, UserEntity user, Date refreshTokenExpiresIn) {
        return JWT.create()
                .withJWTId(account.getActivationCode())
                .withClaim(TYPE_CLAIM, tokenRefreshProperties.getType())
                .withSubject(user.getUsername())
                .withExpiresAt(refreshTokenExpiresIn)
                .withIssuedAt(new Date())
                .withIssuer(serviceProperties.getHost())
                .sign(Algorithm.HMAC256(tokenRefreshProperties.getSecret().getBytes()));
    }

    private String generateAccessToken(Account account, UserEntity user, Date accessTokenExpiresIn) {
        var roles = user.getRoles().stream()
                .map(Role::getType)
                .toList();

        return JWT.create()
                .withJWTId(account.getActivationCode())
                .withClaim(ROLES_CLAIM, roles)
                .withClaim(EMAIL_CLAIM, account.getEmail())
                .withClaim(TYPE_CLAIM, tokenAccessProperties.getType())
                .withSubject(user.getUsername())
                .withExpiresAt(accessTokenExpiresIn)
                .withIssuedAt(new Date())
                .withIssuer(serviceProperties.getHost())
                .sign(Algorithm.HMAC256(tokenAccessProperties.getSecret().getBytes()));
    }
}