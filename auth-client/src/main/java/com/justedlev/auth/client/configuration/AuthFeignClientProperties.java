package com.justedlev.auth.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ConfigurationProperties(prefix = "justedlev-service.auth.client")
public class AuthFeignClientProperties {
    private String url;
    private Duration connectTimeout = Duration.of(10L, ChronoUnit.SECONDS);
    private Duration readTimeout = Duration.of(2L, ChronoUnit.MINUTES);
}
