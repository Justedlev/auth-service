package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.LoginJournalComponent;
import com.justedlev.auth.repository.LoginJournalRepository;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.repository.entity.LoginJournal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginJournalComponentImpl implements LoginJournalComponent {
    private final LoginJournalRepository loginJournalRepository;

    @Override
    @Transactional
    public void createLoginJournal(UserEntity user, Account account) {
        var loginJournal = LoginJournal.builder()
                .createdBy(user.getId())
                .accountId(account.getId())
                .build();

        loginJournalRepository.save(loginJournal);
    }
}
