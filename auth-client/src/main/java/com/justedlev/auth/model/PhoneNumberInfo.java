package com.justedlev.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhoneNumberInfo implements Serializable {
    private String national;
    private String international;
    private Integer countryCode;
    private String regionCode;
}
