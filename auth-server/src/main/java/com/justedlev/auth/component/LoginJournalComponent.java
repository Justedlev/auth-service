package com.justedlev.auth.component;

import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;

public interface LoginJournalComponent {
    void createLoginJournal(UserEntity user, Account account);
}
