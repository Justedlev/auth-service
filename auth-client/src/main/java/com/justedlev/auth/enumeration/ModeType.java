package com.justedlev.auth.enumeration;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

public enum ModeType {
    ONLINE, OFFLINE, HIDDEN, SLEEP;

    public static Optional<ModeType> getByName(String name) {
        return Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .flatMap(current -> Stream.of(ModeType.values())
                        .filter(roleType -> roleType.name().equalsIgnoreCase(current))
                        .findFirst());
    }
}
