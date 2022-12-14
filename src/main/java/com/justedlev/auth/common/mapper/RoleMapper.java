package com.justedlev.auth.common.mapper;

import com.justedlev.auth.component.base.EntityMapper;
import com.justedlev.auth.component.base.ResponseMapper;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.model.response.RoleResponse;
import com.justedlev.auth.repository.entity.Role;

public interface RoleMapper extends ResponseMapper<Role, RoleResponse>, EntityMapper<RoleRequest, Role> {
}
