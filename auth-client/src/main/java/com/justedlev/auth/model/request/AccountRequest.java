package com.justedlev.auth.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justedlev.auth.enumeration.Gender;
import com.justedlev.auth.model.converter.LowerCaseConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String email;
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime birthDate;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
}
