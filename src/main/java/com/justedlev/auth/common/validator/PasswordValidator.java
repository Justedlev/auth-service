package com.justedlev.auth.common.validator;

public interface PasswordValidator {
    void validate(String hash, String rawPassword);
}
