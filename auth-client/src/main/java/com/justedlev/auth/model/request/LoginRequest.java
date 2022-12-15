package com.justedlev.auth.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justedlev.auth.model.converter.LowerCaseConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String email;
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String nickname;
    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
