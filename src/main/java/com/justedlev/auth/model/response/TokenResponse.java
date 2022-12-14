package com.justedlev.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponse {
    private String accessToken;
    private String tokenType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date accessTokenExpiresIn;
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date refreshTokenExpiresIn;
}
