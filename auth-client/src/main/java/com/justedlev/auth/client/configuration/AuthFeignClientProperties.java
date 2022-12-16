package com.justedlev.auth.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "justedlev-service.auth.client")
public class AuthFeignClientProperties {
    private String url;
    private Duration connectTimeout;
    private Duration readTimeout;
}
