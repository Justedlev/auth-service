package com.justedlev.auth.common.converter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justedlev.auth.repository.entity.json.Mode;
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
public class ModeConverter  implements AttributeConverter<Mode, String> {
    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Mode attribute) {
        return Optional.ofNullable(attribute)
                .map(this::convertObjectToJson)
                .orElse(null);
    }

    @Override
    public Mode convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(this::convertJsonToObject)
                .orElse(null);
    }

    private String convertObjectToJson(Mode mode) {
        return Try.of(() -> mapper.writeValueAsString(mode))
                .onFailure(ex -> log.error("Failed to convert Mode object to json : {}", ex.getMessage()))
                .getOrNull();
    }

    private Mode convertJsonToObject(String json) {
        return Try.of(() -> mapper.readValue(json, Mode.class))
                .onFailure(ex -> log.error("Failed to convert json to Mode object : {}", ex.getMessage()))
                .getOrNull();
    }
}
