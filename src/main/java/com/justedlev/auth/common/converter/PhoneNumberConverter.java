package com.justedlev.auth.common.converter;

import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;

public interface PhoneNumberConverter {
    PhoneNumberInfo convert(String phoneNumber);
}
