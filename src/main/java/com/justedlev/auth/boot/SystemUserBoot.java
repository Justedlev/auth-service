package com.justedlev.auth.boot;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.properties.ServiceProperties;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.util.DateTimeUtils;
import com.justedlev.auth.util.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Component
@Order(value = 2)
@RequiredArgsConstructor
public class SystemUserBoot implements ApplicationRunner {
    private final ServiceProperties serviceProperties;
    private final PasswordEncoder passwordEncoder;
    private final UserComponent userComponent;
    private final RoleComponent roleComponent;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        userComponent.getByUsername(serviceProperties.getName().toLowerCase())
                .ifPresentOrElse(this::updatePassword, this::createSystemUser);
    }

    private void updatePassword(UserEntity user) {
        final var password = Generator.generatePassword();
        user.setHashCode(passwordEncoder.encode(password));
        userComponent.save(user);
        log.info("For system user {} changed password: {}", serviceProperties.getEmail(), password);
    }

    private void createSystemUser() {
        final var password = Generator.generatePassword();
        var superAdminRole = roleComponent.getAll();
        var superAdmin = UserEntity.builder()
                .username(serviceProperties.getName().toLowerCase())
                .hashCode(passwordEncoder.encode(password))
                .status(UserStatusCode.ACTUAL)
                .roles(superAdminRole)
                .lastHashes(Collections.emptyList())
                .account(createSystemAccount())
                .build();

        userComponent.save(superAdmin);
        log.info("System user {} successfully created with password: {}", serviceProperties.getEmail(), password);
    }

    private Account createSystemAccount() {
        return Account.builder()
                .nickname(serviceProperties.getName().toLowerCase())
                .email(serviceProperties.getEmail().toLowerCase())
                .status(AccountStatusCode.ACTUAL)
                .birthDate(DateTimeUtils.nowTimestamp())
                .firstName("Justedlev")
                .lastName("Hub")
                .build();
    }
}
