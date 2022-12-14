package com.justedlev.auth.restclient.storage;

import com.justedlev.auth.restclient.storage.config.StorageApiClientConfiguration;
import com.justedlev.auth.restclient.storage.model.response.UploadFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "StorageApiClient", url = "${rest-client.jstorage}", configuration = StorageApiClientConfiguration.class)
public interface StorageApiClient {
    @PostMapping(value = "/v1/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<UploadFileResponse> upload(@RequestPart List<MultipartFile> files);

    @DeleteMapping(value = "/v1/file/{fileName}/delete")
    Boolean delete(@PathVariable String fileName);
}
