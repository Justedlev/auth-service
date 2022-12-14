package com.justedlev.auth.scheduler;

import com.justedlev.auth.properties.AuthProperties;
import com.justedlev.auth.properties.TokenAccessProperties;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.component.AccountComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModeTask {
    private final TokenAccessProperties tokenAccessProperties;
    private final AuthProperties properties;
    private final AccountComponent accountComponent;

    @Async
    @Scheduled(cron = "${auth.cron.offline-mode-in}")
    public void offlineModeTask() {
        updateMode(Set.of(ModeType.HIDDEN, ModeType.ONLINE, ModeType.SLEEP),
                ModeType.OFFLINE, tokenAccessProperties.getExpirationTime());
    }

    @Async
    @Scheduled(cron = "${auth.cron.sleep-mode-in}")
    public void sleepModeTask() {
        updateMode(Set.of(ModeType.HIDDEN, ModeType.ONLINE), ModeType.SLEEP, properties.getActivityTime());
    }

    private void updateMode(Set<ModeType> fromModes, ModeType toMode, Duration duration) {
        var activeAccounts = getActiveAccount(fromModes, duration);
        activeAccounts.forEach(current -> current.setMode(toMode));
        accountComponent.saveAll(activeAccounts);
        log.info("Changed {} inactive accounts to mode : {}", activeAccounts.size(), toMode);
    }

    private List<Account> getActiveAccount(Set<ModeType> fromModes, Duration duration) {
        var filter = AccountFilter.builder()
                .modes(fromModes)
                .build();
        var now = System.currentTimeMillis();

        return accountComponent.getByFilter(filter)
                .parallelStream()
                .filter(current -> filterOutByModeAt(current, now, duration))
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
