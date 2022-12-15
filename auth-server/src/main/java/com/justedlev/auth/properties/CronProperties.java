package com.justedlev.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "justedlev-service.auth.cron")
public class CronProperties {
    private String offlineModeIn;
    private String sleepModeIn;
}
