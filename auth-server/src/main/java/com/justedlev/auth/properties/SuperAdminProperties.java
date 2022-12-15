package com.justedlev.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "justedlev-service.auth.super-admin")
public class SuperAdminProperties {
    private Boolean autoCreate;
    private String password;
    private String email;
    private String nickname;
}
