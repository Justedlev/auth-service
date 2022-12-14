package com.justedlev.auth.common.validator.impl;

import com.justedlev.auth.common.validator.LoginRequestValidator;
import com.justedlev.auth.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginRequestValidatorImpl implements LoginRequestValidator {
    private final EmailValidator emailValidator;

    @Override
    public void validate(LoginRequest request) {
        if (ObjectUtils.isEmpty(request))
            throw new IllegalArgumentException("Wrong data");

        String message = "Incorrect email, nickname or password";

        if (StringUtils.isBlank(request.getPassword()))
            throw new IllegalArgumentException(message);

        if (StringUtils.isBlank(request.getNickname()) && !emailValidator.isValid(request.getEmail()))
            throw new IllegalArgumentException(message);
    }
}
