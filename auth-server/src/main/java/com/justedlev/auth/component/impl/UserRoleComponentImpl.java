package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.component.UserRoleComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserRoleComponentImpl implements UserRoleComponent {
    private final UserComponent userComponent;
    private final RoleComponent roleComponent;

    @Override
    public List<Role> getRoles(String username) {
        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();

        return userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .map(UserEntity::getRoles)
                .orElse(Collections.emptyList());
    }

    @Override
    public UserEntity addRoles(String username, Set<String> roleTypes) {
        var roleEntities = getRoles(roleTypes);

        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();
        var entity = userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> throwUserNotExists(username));
        entity.getRoles().addAll(roleEntities);

        return userComponent.save(entity);
    }

    @Override
    public UserEntity removeRoles(String username, Set<String> roleTypes) {
        var roleEntities = getRoles(roleTypes);

        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();
        var entity = userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> throwUserNotExists(username));
        roleEntities.forEach(entity.getRoles()::remove);

        return userComponent.save(entity);
    }

    private List<Role> getRoles(Set<String> roleTypes) {
        return Optional.ofNullable(roleTypes)
                .filter(CollectionUtils::isNotEmpty)
                .map(roleComponent::get)
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Roles %s not exists", roleTypes)));
    }

    private EntityNotFoundException throwUserNotExists(String username) {
        throw new EntityNotFoundException(String.format(ExceptionConstant.USER_NOT_EXISTS, username));
    }
}
