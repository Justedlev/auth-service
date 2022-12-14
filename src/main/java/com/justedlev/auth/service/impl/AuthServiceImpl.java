package com.justedlev.auth.service.impl;

import com.justedlev.auth.common.validator.LoginRequestValidator;
import com.justedlev.auth.common.validator.PasswordValidator;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.LoginJournalComponent;
import com.justedlev.auth.component.RegistrationComponent;
import com.justedlev.auth.component.TokenComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.model.request.LoginRefreshRequest;
import com.justedlev.auth.model.request.LoginRequest;
import com.justedlev.auth.model.request.RegistrationRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.TokenResponse;
import com.justedlev.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenComponent tokenComponent;
    private final AccountComponent accountComponent;
    private final RegistrationComponent registrationComponent;
    private final LoginJournalComponent loginJournalComponent;
    private final LoginRequestValidator loginRequestValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public ReportResponse signup(RegistrationRequest request) {
        return registrationComponent.registration(request);
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        loginRequestValidator.validate(request);
        var account = accountComponent.getByEmail(request.getEmail())
                .or(() -> accountComponent.getByNickname(request.getNickname()))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (account.getStatus().equals(AccountStatusCode.ACTUAL) &&
                account.getUser().getStatus().equals(UserStatusCode.ACTUAL)) {
            passwordValidator.validate(account.getUser().getHashCode(), request.getPassword());
            loginJournalComponent.createLoginJournal(account.getUser(), account);

            return tokenComponent.generateToken(account.getUser(), account);
        } else {
            throw new IllegalStateException(String.format("Account in status %s:%s",
                    account.getStatus(), account.getUser().getStatus()));
        }
    }

    @Override
    public TokenResponse loginRefresh(LoginRefreshRequest request) {
        var decodedRefreshToken = tokenComponent.verifyRefreshToken(request.getRefreshToken());
        var nickname = decodedRefreshToken.getSubject();
        var account = accountComponent.getByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return tokenComponent.generateToken(account.getUser(), account);
    }

    @Override
    public ReportResponse logout(String nickname) {
        return null;
    }
}
