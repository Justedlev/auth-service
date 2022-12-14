package com.justedlev.auth.common.converter.impl;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Locale;
import java.util.Optional;

public class LowerCaseConverter extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        return Optional.ofNullable(value)
                .map(current -> current.toLowerCase(Locale.ROOT))
                .orElse(null);
    }
}
