package com.justedlev.auth.repository.custom;

import com.justedlev.auth.repository.custom.filter.LoginJournalFilter;
import com.justedlev.auth.repository.entity.LoginJournal;

import java.util.List;

public interface LoginJournalCustomRepository {
    List<LoginJournal> findByFilter(LoginJournalFilter filter);
}
