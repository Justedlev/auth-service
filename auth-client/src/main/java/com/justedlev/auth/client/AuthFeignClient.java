package com.justedlev.auth.client;

import com.justedlev.auth.client.configuration.AuthFeignClientConfiguration;
import com.justedlev.auth.model.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "auth-api-client",
        url = "${justedlev-service.auth.host}",
        configuration = AuthFeignClientConfiguration.class
)
public interface AuthFeignClient {
    @PostMapping(value = "/v1/account/sleep")
    List<AccountResponse> sleep();

    @PostMapping(value = "/v1/account/offline")
    List<AccountResponse> offline();
}
