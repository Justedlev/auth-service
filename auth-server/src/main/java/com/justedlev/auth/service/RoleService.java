package com.justedlev.auth.service;

import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAll();

    List<RoleResponse> getAllByRoleGroup(String roleGroup);

    RoleResponse get(String roleType);

    ReportResponse add(RoleRequest request);

    ReportResponse edit(RoleRequest request);

    ReportResponse delete(String roleType);
}
