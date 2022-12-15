package com.justedlev.auth.common.mapper.impl;

import com.justedlev.auth.common.mapper.RoleMapper;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.model.response.RoleResponse;
import com.justedlev.auth.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleMapperImpl implements RoleMapper {
    private final ModelMapper defaultMapper;

    @Override
    public RoleResponse mapToResponse(Role entity) {
        return defaultMapper.map(entity, RoleResponse.class);
    }

    @Override
    public List<RoleResponse> mapToResponse(List<Role> entities) {
        return entities.parallelStream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Role mapToEntity(RoleRequest request) {
        return defaultMapper.map(request, Role.class);
    }

    @Override
    public List<Role> mapToEntity(List<RoleRequest> requests) {
        return requests.parallelStream()
                .map(this::mapToEntity)
                .toList();
    }
}
