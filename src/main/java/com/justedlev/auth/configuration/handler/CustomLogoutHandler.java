package com.justedlev.auth.configuration.handler;

import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.TokenComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final AccountComponent accountComponent;
    private final TokenComponent tokenComponent;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional.ofNullable(tokenComponent.getToken(request))
                .map(tokenComponent::getSubject)
                .flatMap(accountComponent::getByNickname)
                .ifPresent(current -> {
                    current.setMode(ModeType.OFFLINE);
                    accountComponent.save(current);
                });
    }
}
