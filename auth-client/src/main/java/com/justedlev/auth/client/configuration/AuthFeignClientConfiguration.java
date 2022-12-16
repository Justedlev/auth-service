package com.justedlev.auth.client.configuration;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class AuthFeignClientConfiguration {
    @Value("${justedlev-service.auth.read-timeout:1m}")
    private Duration readTimeout;
    @Value("${justedlev-service.auth.connect-timeout:10s}")
    private Duration connectTimeout;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                connectTimeout.get(ChronoUnit.MILLIS),
                TimeUnit.MILLISECONDS,
                readTimeout.get(ChronoUnit.MILLIS),
                TimeUnit.MILLISECONDS,
                Boolean.FALSE
        );
    }
}
