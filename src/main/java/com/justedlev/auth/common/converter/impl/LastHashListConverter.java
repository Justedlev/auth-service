package com.justedlev.auth.common.converter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justedlev.auth.repository.entity.json.LastHash;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Converter
@RequiredArgsConstructor
public class LastHashListConverter implements AttributeConverter<List<LastHash>, String> {
    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<LastHash> attribute) {
        return Optional.ofNullable(attribute)
                .map(this::convertObjectToJson)
                .orElse(null);
    }

    @Override
    public List<LastHash> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(this::convertJsonToObject)
                .orElse(null);
    }

    private String convertObjectToJson(List<LastHash> hashes) {
        return Try.of(() -> mapper.writeValueAsString(hashes))
                .onFailure(ex -> log.error("Failed to convert list of LastHash objects to json : {}", ex.getMessage()))
                .getOrNull();
    }

    private List<LastHash> convertJsonToObject(String json) {
        return Try.of(() -> mapper.readValue(json, LastHash[].class))
                .onFailure(ex -> log.error("Failed to convert json to list of LastHash objects : {}", ex.getMessage()))
                .map(List::of)
                .getOrElse(Collections.emptyList());
    }
}
