package com.justedlev.auth.controller;

import com.justedlev.auth.constant.CookieKeyConstant;
import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.model.request.LoginRefreshRequest;
import com.justedlev.auth.model.request.LoginRequest;
import com.justedlev.auth.model.request.RegistrationRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.TokenResponse;
import com.justedlev.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(EndpointConstant.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = EndpointConstant.SIGNUP)
    public ResponseEntity<ReportResponse> signup(@Valid @RequestBody RegistrationRequest form) {
        log.debug("POST method '{}' is work", "/signup");

        return ResponseEntity.ok(authService.signup(form));
    }

    @PostMapping(value = EndpointConstant.LOGIN)
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        var response = authService.login(request);

        return setTokenInCookie(response);
    }

    @PostMapping(value = EndpointConstant.LOGIN_REFRESH)
    public ResponseEntity<TokenResponse> loginRefresh(@Valid @RequestBody LoginRefreshRequest request) {
        var response = authService.loginRefresh(request);

        return setTokenInCookie(response);
    }

//    @GetMapping(value = AuthEndpoint.LOGOUT)
//    public ResponseEntity<ReportResponse> logout() {
//        var response = authService.logout(null);
//
//        return ResponseEntity.ok(response);
//    }

    private ResponseEntity<TokenResponse> setTokenInCookie(TokenResponse token) {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookies(token))
                .body(token);
    }

    private String[] cookies(TokenResponse token) {
        return new String[]{
                cookie(CookieKeyConstant.ATK, token.getAccessToken(), token.getAccessTokenExpiresIn().getTime()),
                cookie(CookieKeyConstant.RTK, token.getRefreshToken(), token.getRefreshTokenExpiresIn().getTime())
        };
    }

    private String cookie(String key, String value, long maxAge) {
        return ResponseCookie.from(key, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .build()
                .toString();
    }
}
