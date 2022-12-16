package com.justedlev.auth;

import com.justedlev.auth.properties.*;
import com.justedlev.storage.client.StorageFeignClient;
import com.justedlev.storage.client.configuration.StorageFeignClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(clients = StorageFeignClient.class)
@EnableConfigurationProperties({
        AuthProperties.class,
        CorsProperties.class,
        SuperAdminProperties.class,
        CronProperties.class,
        ServiceProperties.class,
        TokenAccessProperties.class,
        TokenRefreshProperties.class,
        StorageFeignClientProperties.class
})
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
