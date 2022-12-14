package com.justedlev.auth.model.formdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadPhotoFormData {
    @NotBlank(message = "Nickname cannot be empty.")
    private String nickname;
    private MultipartFile photo;
}
