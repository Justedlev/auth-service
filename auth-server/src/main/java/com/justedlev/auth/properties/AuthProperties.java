package com.justedlev.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "justedlev-service.auth")
public class AuthProperties {
    private Boolean fillUsers;
    private Integer minPasswordLength;
    private Integer countLastHashes;
    private Duration deactivationTime;
    private Integer codeLength;
    private String clientId;
    private String clientSecret;
    private Duration activityTime;
}
