package com.justedlev.auth.common.converter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Slf4j
@Converter
@Component
@RequiredArgsConstructor
public class PhoneNumberInfoConverter implements AttributeConverter<PhoneNumberInfo, String> {
    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(PhoneNumberInfo attribute) {
        return Optional.ofNullable(attribute)
                .map(this::convertObjectToJson)
                .orElse(null);
    }

    @Override
    public PhoneNumberInfo convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(this::convertJsonToObject)
                .orElse(null);
    }

    private String convertObjectToJson(PhoneNumberInfo phoneNumberInfo) {
        return Try.of(() -> mapper.writeValueAsString(phoneNumberInfo))
                .onFailure(ex -> log.error("Failed to convert PhoneNumberInfo object to json : {}", ex.getMessage()))
                .getOrNull();
    }

    private PhoneNumberInfo convertJsonToObject(String json) {
        return Try.of(() -> mapper.readValue(json, PhoneNumberInfo.class))
                .onFailure(ex -> log.error("Failed to convert json to PhoneNumberInfo object : {}", ex.getMessage()))
                .getOrNull();
    }
}
