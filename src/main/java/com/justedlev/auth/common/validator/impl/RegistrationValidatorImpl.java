package com.justedlev.auth.common.validator.impl;

import com.justedlev.auth.common.validator.RegistrationValidator;
import com.justedlev.auth.properties.AuthProperties;
import com.justedlev.auth.model.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationValidatorImpl implements RegistrationValidator {
    private final AuthProperties properties;
    private final EmailValidator emailValidator;

    @Override
    public void validate(RegistrationRequest request) {
        if (StringUtils.isBlank(request.getNickname())) {
            throw new IllegalArgumentException("Nickname can not be empty");
        }

        if (!emailValidator.isValid(request.getEmail())) {
            throw new IllegalArgumentException(String.format("Email %s not valid", request.getEmail()));
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password can not be empty");
        }

        if (request.getEmail().length() < properties.getMinPasswordLength()) {
            throw new IllegalArgumentException(
                    String.format("Password need to be more than %s chars", properties.getMinPasswordLength()));
        }
    }
}
