package com.justedlev.auth.enumeration;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

public enum Gender {
    MALE, FEMALE;

    public static Optional<Gender> getByName(String name) {
        return Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .flatMap(current -> Stream.of(Gender.values())
                        .filter(roleType -> roleType.name().equalsIgnoreCase(current))
                        .findFirst());
    }
}
