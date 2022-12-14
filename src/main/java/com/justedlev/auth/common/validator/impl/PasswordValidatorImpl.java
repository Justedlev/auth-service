package com.justedlev.auth.common.validator.impl;

import com.justedlev.auth.common.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidatorImpl implements PasswordValidator {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void validate(String hash, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, hash))
            throw new IllegalArgumentException("Incorrect password");
    }
}
