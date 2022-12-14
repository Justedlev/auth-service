package com.justedlev.auth.common.converter.impl;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.auth.common.converter.PhoneNumberConverter;
import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberConverterImpl implements PhoneNumberConverter {
    private final PhoneNumberUtil phoneNumberUtil;

    @Override
    @SneakyThrows
    public PhoneNumberInfo convert(String phoneNumber) {
        var parsedPhoneNumber = phoneNumberUtil.parse(phoneNumber, null);
        var regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedPhoneNumber);
        var national = phoneNumberUtil.format(parsedPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        var international = phoneNumberUtil.format(parsedPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

        return PhoneNumberInfo.builder()
                .regionCode(regionCode)
                .countryCode(parsedPhoneNumber.getCountryCode())
                .national(national)
                .international(international)
                .build();
    }
}
