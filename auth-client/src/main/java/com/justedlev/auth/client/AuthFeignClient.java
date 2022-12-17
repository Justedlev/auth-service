package com.justedlev.auth.client;

import com.justedlev.auth.client.configuration.AuthFeignClientConfiguration;
import com.justedlev.auth.model.request.UpdateAccountModeRequest;
import com.justedlev.auth.model.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "auth-api-client",
        url = "${justedlev-service.auth.client.url}",
        configuration = AuthFeignClientConfiguration.class
)
public interface AuthFeignClient {
    @PostMapping(value = "/v1/account/update-mode")
    List<AccountResponse> updateMode(@RequestBody UpdateAccountModeRequest request);
}
