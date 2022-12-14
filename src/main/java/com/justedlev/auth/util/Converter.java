package com.justedlev.auth.util;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class Converter {
    private Converter() {
        throw new IllegalStateException("Util class");
    }

    public static Set<String> toLowerCase(Collection<String> strings) {
        return strings.parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
