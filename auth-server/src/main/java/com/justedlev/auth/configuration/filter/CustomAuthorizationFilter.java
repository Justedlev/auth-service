package com.justedlev.auth.configuration.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.TokenComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.common.mapper.UserDetailsMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final UserComponent userComponent;
    private final TokenComponent tokenComponent;
    private final UserDetailsMapper userDetailsMapper;
    private final AccountComponent accountComponent;

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) {
        try {
            Optional.ofNullable(tokenComponent.getToken(request))
                    .map(tokenComponent::verifyAccessToken)
                    .map(DecodedJWT::getSubject)
                    .flatMap(userComponent::getByUsername)
                    .filter(current -> current.getStatus().equals(UserStatusCode.ACTUAL))
                    .filter(current -> current.getAccount().getStatus().equals(AccountStatusCode.ACTUAL))
                    .ifPresent(current -> authenticateUser(current, request));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        chain.doFilter(request, response);
    }

    private void authenticateUser(UserEntity current, HttpServletRequest request) {
        var userDetails = userDetailsMapper.map(current);
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        updateMode(current.getAccount());
    }

    private void updateMode(Account account) {
        account.setMode(ModeType.ONLINE);
        accountComponent.save(account);
    }
}
