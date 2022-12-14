package com.justedlev.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Getter
@Setter
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "auth.token.access")
public class TokenAccessProperties {
    private String type;
    private String secret;
    private Duration expirationTime;
}
