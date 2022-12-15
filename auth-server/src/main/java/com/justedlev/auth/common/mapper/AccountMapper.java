package com.justedlev.auth.common.mapper;

import com.justedlev.auth.component.base.EntityMapper;
import com.justedlev.auth.component.base.ResponseMapper;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.repository.entity.Account;

public interface AccountMapper extends ResponseMapper<Account, AccountResponse>, EntityMapper<AccountRequest, Account> {
}
