package com.justedlev.auth.util;

import org.apache.commons.lang3.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

public final class DateTimeUtils {
    private DateTimeUtils() {
        throw new IllegalStateException("Util class");
    }

    public static Timestamp toTimestamp(Long millis) {
        return Timestamp.valueOf(LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date.getTime());
    }

    public static LocalDateTime toLocalDateTime(long millis) {
        return toLocalDateTime(millis, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(long millis, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId);
    }

    public static Long daysBetween(Timestamp from, Timestamp to) {
        if (ObjectUtils.isEmpty(from) || ObjectUtils.isEmpty(to)) {
            return 0L;
        }
        return ChronoUnit.DAYS.between(from.toLocalDateTime(), to.toLocalDateTime());
    }

    public static Long daysBetweenNow(Timestamp from) {
        return daysBetween(from, nowTimestamp());
    }

    public static Timestamp nowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Long toMillisOrNull(Date timestamp) {
        return Optional.ofNullable(timestamp)
                .map(Date::getTime)
                .orElse(null);
    }
}
