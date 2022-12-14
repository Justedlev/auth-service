package com.justedlev.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.Gender;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private String email;
    private PhoneNumberInfo phoneNumberInfo;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountStatusCode status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeType mode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date registrationDate;
    private String photoUrl;
}
