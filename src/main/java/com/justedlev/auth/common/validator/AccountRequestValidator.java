package com.justedlev.auth.common.validator;

import com.justedlev.auth.model.request.AccountRequest;

public interface AccountRequestValidator {
    void validate(AccountRequest request);
}
