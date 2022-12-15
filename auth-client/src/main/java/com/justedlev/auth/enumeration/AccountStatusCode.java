package com.justedlev.auth.enumeration;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountStatusCode {
    ACTUAL, UNCONFIRMED, RESTORED, DEACTIVATED, DELETED;

    public static Optional<AccountStatusCode> getByName(String name) {
        return Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .flatMap(current -> Stream.of(AccountStatusCode.values())
                        .filter(roleType -> roleType.name().equalsIgnoreCase(current))
                        .findFirst());
    }
}
