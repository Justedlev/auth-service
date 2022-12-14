package com.justedlev.auth.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justedlev.auth.common.converter.impl.LowerCaseConverter;
import lombok.*;

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
