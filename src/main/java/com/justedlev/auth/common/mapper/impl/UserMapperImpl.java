package com.justedlev.auth.common.mapper.impl;

import com.justedlev.auth.model.request.UserRequest;
import com.justedlev.auth.model.response.UserResponse;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.common.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ModelMapper defaultMapper;

    @Override
    public UserResponse mapToResponse(UserEntity entity) {
        return defaultMapper.map(entity, UserResponse.class);
    }

    @Override
    public List<UserResponse> mapToResponse(List<UserEntity> entities) {
        return entities.parallelStream()
                .distinct()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserEntity mapToEntity(UserRequest request) {
        return null;
    }

    @Override
    public List<UserEntity> mapToEntity(List<UserRequest> requests) {
        return Collections.emptyList();
    }
}
