package com.justedlev.auth.component;

import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.repository.entity.Role;
import com.justedlev.auth.component.base.CreateEntity;
import com.justedlev.auth.component.base.DeleteEntity;
import com.justedlev.auth.component.base.SaveEntity;
import com.justedlev.auth.component.base.UpdateEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleComponent extends UpdateEntity<RoleRequest, Role>, CreateEntity<RoleRequest, Role>,
        SaveEntity<Role>, DeleteEntity<Role> {

    List<Role> getAll();

    List<Role> getAllByRoleGroup(Set<String> roleGroups);

    Optional<Role> get(String roleType);

    List<Role> get(Set<String> roleTypes);

    Role delete(String roleType);

    Role update(RoleRequest request);

    Integer getPageCount(PaginationRequest request);
}
