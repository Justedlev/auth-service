package com.justedlev.auth.restclient.storage;

import com.justedlev.auth.restclient.storage.config.StorageApiClientConfiguration;
import com.justedlev.storage.client.StorageFeignClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "StorageApiClient", url = "${rest-client.jstorage}", configuration = StorageApiClientConfiguration.class)
public interface StorageApiClient extends StorageFeignClient {
}
