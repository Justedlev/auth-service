package com.justedlev.auth.common.validator;

import com.justedlev.auth.model.request.RegistrationRequest;

public interface RegistrationValidator {
    void validate(RegistrationRequest request);
}
