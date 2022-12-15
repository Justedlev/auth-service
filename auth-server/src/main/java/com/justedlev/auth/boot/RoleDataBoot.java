package com.justedlev.auth.boot;

import com.justedlev.auth.enumeration.RoleType;
import com.justedlev.auth.repository.entity.Role;
import com.justedlev.auth.component.RoleComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(value = 1)
@RequiredArgsConstructor
public class RoleDataBoot implements ApplicationRunner {
    private final RoleComponent roleComponent;
    private static final Map<String, String> roleMap = Arrays.stream(RoleType.values())
            .collect(Collectors.toMap(RoleType::getType, RoleType::getGroup));

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        var roles = roleComponent.get(roleMap.keySet())
                .stream()
                .collect(Collectors.toMap(Role::getType, Function.identity()));
        var rolesForSave = roleMap.entrySet().stream()
                .filter(current -> !roles.containsKey(current.getKey()))
                .map(current -> getRoleEntity(current.getKey(), current.getValue()))
                .toList();

        if (CollectionUtils.isNotEmpty(rolesForSave)) {
            var res = roleComponent.saveAll(rolesForSave)
                    .stream()
                    .map(Role::getType)
                    .collect(Collectors.toSet());
            log.info("Created roles : {}", res);
        }
    }

    private Role getRoleEntity(String roleType, String roleGroup) {
        return Role.builder()
                .type(roleType)
                .group(roleGroup)
                .build();
    }
}
