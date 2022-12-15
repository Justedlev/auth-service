package com.justedlev.auth.common.converter;

import com.justedlev.auth.model.PhoneNumberInfo;

public interface PhoneNumberConverter {
    PhoneNumberInfo convert(String phoneNumber);
}
