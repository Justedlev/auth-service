package com.justedlev.auth.client.configuration;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class AuthFeignClientConfiguration {
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                10,
                TimeUnit.SECONDS,
                1,
                TimeUnit.MINUTES,
                Boolean.FALSE
        );
    }
}
