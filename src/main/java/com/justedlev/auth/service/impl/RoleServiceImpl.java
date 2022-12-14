package com.justedlev.auth.service.impl;

import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.RoleResponse;
import com.justedlev.auth.service.RoleService;
import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.common.mapper.ReportMapper;
import com.justedlev.auth.common.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final ReportMapper reportMapper;
    private final RoleComponent roleComponent;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAll() {
        var entities = this.roleComponent.getAll();

        return roleMapper.mapToResponse(entities);
    }

    @Override
    public List<RoleResponse> getAllByRoleGroup(String roleGroup) {
        var entities = this.roleComponent.getAllByRoleGroup(Set.of(roleGroup));

        return roleMapper.mapToResponse(entities);
    }

    @Override
    public RoleResponse get(String roleType) {
        var entity = roleComponent.get(roleType)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.ROLE_NOT_EXISTS, roleType)));

        return roleMapper.mapToResponse(entity);
    }

    @Override
    public ReportResponse add(RoleRequest request) {
        this.roleComponent.create(request);

        return reportMapper.toReport();
    }

    @Override
    public ReportResponse edit(RoleRequest request) {
        roleComponent.update(request);

        return reportMapper.toReport();
    }

    @Override
    public ReportResponse delete(String roleType) {
        var entity = roleComponent.get(roleType)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.ROLE_NOT_EXISTS, roleType)));
        this.roleComponent.delete(entity);

        return reportMapper.toReport();
    }
}
