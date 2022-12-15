package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.repository.RoleRepository;
import com.justedlev.auth.repository.custom.filter.RoleFilter;
import com.justedlev.auth.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleComponentImpl implements RoleComponent {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getAllByRoleGroup(Set<String> roleGroups) {
        var filter = RoleFilter.builder()
                .groups(roleGroups)
                .build();

        return roleRepository.findByFilter(filter);
    }

    @Override
    public Optional<Role> get(String roleType) {
        return get(Set.of(roleType)).parallelStream()
                .findFirst();
    }

    @Override
    public List<Role> get(Set<String> roleTypes) {
        var filter = RoleFilter.builder()
                .types(roleTypes)
                .build();

        return roleRepository.findByFilter(filter);
    }

    @Override
    public Role delete(String roleType) {
        var entity = get(roleType)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.ROLE_NOT_EXISTS, roleType)));

        return delete(entity);
    }

    @Override
    public Role delete(Role role) {
        roleRepository.delete(role);

        return role;
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public List<Role> saveAll(List<Role> entities) {
        return roleRepository.saveAll(entities);
    }

    @Override
    public Role create(RoleRequest request) {
        return get(request.getType())
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .type(request.getType())
                        .group(request.getGroup())
                        .about(request.getAbout())
                        .build()));
    }

    @Override
    public Role update(Role entity, RoleRequest request) {
        if (ObjectUtils.isNotEmpty(request.getGroup())) {
            entity.setGroup(request.getGroup());
        }

        if (ObjectUtils.isNotEmpty(request.getAbout())) {
            entity.setAbout(request.getAbout());
        }

        return save(entity);
    }

    @Override
    public Role update(RoleRequest request) {
        var role = get(request.getType())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.ROLE_NOT_EXISTS, request.getType())));

        return update(role, request);
    }

    @Override
    public Integer getPageCount(PaginationRequest request) {
        return null;
    }
}
