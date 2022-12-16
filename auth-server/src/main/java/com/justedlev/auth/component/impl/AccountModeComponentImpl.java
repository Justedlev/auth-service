package com.justedlev.auth.component.impl;

import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.component.AccountModeComponent;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.model.request.UpdateAccountModeRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.properties.AuthProperties;
import com.justedlev.auth.properties.TokenAccessProperties;
import com.justedlev.auth.repository.AccountRepository;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountModeComponentImpl implements AccountModeComponent {
    private final TokenAccessProperties tokenAccessProperties;
    private final AuthProperties properties;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        var activeAccounts = getActiveAccount(request);
        activeAccounts.forEach(current -> current.setMode(request.getToMode()));
        var res = accountRepository.saveAll(activeAccounts);
        log.info("Changed {} inactive accounts to mode : {}", activeAccounts.size(), request.getToMode());

        return accountMapper.mapToResponse(res);
    }

    private List<Account> getActiveAccount(UpdateAccountModeRequest request) {
        var filter = AccountFilter.builder()
                .modes(request.getFromModes())
                .build();
        var now = System.currentTimeMillis();
        var duration = getDuration(request.getToMode());

        return accountRepository.findByFilter(filter)
                .parallelStream()
                .filter(current -> filterOutByModeAt(current, now, duration))
                .toList();
    }

    private Duration getDuration(ModeType modeType) {
        return switch (modeType) {
            case SLEEP -> properties.getActivityTime();
            case OFFLINE -> tokenAccessProperties.getExpirationTime();
            default -> Duration.of(1, ChronoUnit.MINUTES);
        };
    }

    private boolean filterOutByModeAt(Account account, long now, Duration duration) {
        return Optional.ofNullable(account.getModeAt())
                .map(Timestamp::getTime)
                .map(current -> now - current)
                .map(current -> current >= duration.toMillis())
                .orElse(Boolean.FALSE);
    }
}
