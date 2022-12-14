package com.justedlev.auth.common.validator;

import com.justedlev.auth.model.request.LoginRequest;

public interface LoginRequestValidator {
    void validate(LoginRequest loginRequest);
}
