package com.justedlev.auth.boot;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.properties.SuperAdminProperties;
import com.justedlev.auth.constant.RoleTypeConstant;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
@Order(value = 3)
@RequiredArgsConstructor
public class UserDataBoot implements ApplicationRunner {
    private final SuperAdminProperties superAdminProperties;
    private final PasswordEncoder passwordEncoder;
    private final UserComponent userComponent;
    private final RoleComponent roleComponent;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Optional.ofNullable(superAdminProperties.getAutoCreate())
                .map(BooleanUtils::toBoolean)
                .filter(BooleanUtils::isTrue)
                .map(current -> superAdminProperties.getNickname())
                .map(String::toLowerCase)
                .flatMap(userComponent::getByUsername)
                .ifPresentOrElse(current -> log.info("Super user {} already exists", current.getUsername()), this::createSuperUser);
    }

    private void createSuperUser() {
        var roles = Set.of(
                RoleTypeConstant.ADMIN,
                RoleTypeConstant.SUPER_USER,
                RoleTypeConstant.USER
        );
        var superAdminRole = roleComponent.get(roles);
        var superAdmin = UserEntity.builder()
                .username(superAdminProperties.getNickname().toLowerCase())
                .hashCode(passwordEncoder.encode(superAdminProperties.getPassword()))
                .status(UserStatusCode.ACTUAL)
                .roles(superAdminRole)
                .lastHashes(Collections.emptyList())
                .account(createSuperAccount())
                .build();

        userComponent.save(superAdmin);
        log.info("Super admin {} successfully created", superAdminProperties.getEmail());
    }

    private Account createSuperAccount() {
        return Account.builder()
                .nickname(superAdminProperties.getNickname().toLowerCase())
                .email(superAdminProperties.getEmail().toLowerCase())
                .status(AccountStatusCode.ACTUAL)
                .build();
    }
}