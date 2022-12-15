package com.justedlev.auth.component.impl;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.component.AccountModeComponent;
import com.justedlev.auth.component.command.AccountModeCommand;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.repository.AccountRepository;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountModeComponentImpl implements AccountModeComponent {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountResponse> updateMode(AccountModeCommand command) {
        var activeAccounts = getActiveAccount(command);
        activeAccounts.forEach(current -> current.setMode(command.getToMode()));
        var res = accountRepository.saveAll(activeAccounts);
        log.info("Changed {} inactive accounts to mode : {}", activeAccounts.size(), command.getToMode());

        return accountMapper.mapToResponse(res);
    }

    private List<Account> getActiveAccount(AccountModeCommand command) {
        var filter = AccountFilter.builder()
                .modes(command.getFromModes())
                .build();
        var now = System.currentTimeMillis();

        return accountRepository.findByFilter(filter)
                .parallelStream()
                .filter(current -> filterOutByModeAt(current, now, command.getDuration()))
                .toList();
    }

    private boolean filterOutByModeAt(Account account, long now, Duration duration) {
        return Optional.ofNullable(account.getModeAt())
                .map(Timestamp::getTime)
                .map(current -> now - current)
                .map(current -> current >= duration.toMillis())
                .orElse(Boolean.FALSE);
    }
}
