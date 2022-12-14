package com.justedlev.auth.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class Generator {
    private Generator() {
        throw new IllegalStateException("Util class");
    }

    public static Integer generateNumberCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.generate(() -> randomInt(9))
                .limit(length)
                .forEach(stringBuilder::append);

        return Integer.parseInt(stringBuilder.toString());
    }

    public static int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    public static String generateSecretCode() {
        return generateActivationCode();
    }

    public static String generateActivationCode() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public static String generatePassword() {
        var symbols = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&?";

        return RandomStringUtils.random(RandomUtils.nextInt(12, 32), symbols);
    }
}
