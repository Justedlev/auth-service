package com.justedlev.auth.repository.custom;

import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountCustomRepository {
    List<Account> findByFilter(AccountFilter filter);

    List<Account> findByFilter(AccountFilter filter, Pageable pageable);
}
