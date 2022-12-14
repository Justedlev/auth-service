package com.justedlev.auth.common.mapper.impl;

import com.justedlev.auth.common.converter.PhoneNumberConverter;
import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;
import com.justedlev.auth.repository.entity.json.Photo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper defaultMapper;
    private final PhoneNumberConverter phoneNumberConverter;

    @Override
    public AccountResponse mapToResponse(Account request) {
        var response = defaultMapper.map(request, AccountResponse.class);
        response.setRegistrationDate(request.getCreatedAt());
        Optional.ofNullable(request.getPhoto())
                .map(Photo::getUrl)
                .ifPresent(response::setPhotoUrl);

        return response;
    }

    @Override
    public List<AccountResponse> mapToResponse(List<Account> requests) {
        return requests.stream()
                .map(current -> defaultMapper.map(current, AccountResponse.class))
                .distinct()
                .toList();
    }

    @Override
    public Account mapToEntity(AccountRequest request) {
        var account = defaultMapper.map(request, Account.class);
        var phoneNumber = convertToPhoneInfo(request.getPhoneNumber());
        account.setPhoneNumberInfo(phoneNumber);

        return account;
    }

    @Override
    public List<Account> mapToEntity(List<AccountRequest> requests) {
        return requests.parallelStream()
                .map(this::mapToEntity)
                .toList();
    }

    private PhoneNumberInfo convertToPhoneInfo(String phoneNumber) {
        return Optional.ofNullable(phoneNumber)
                .filter(StringUtils::isNotBlank)
                .map(phoneNumberConverter::convert)
                .orElse(null);
    }
}
