package com.justedlev.auth.boot;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.properties.AuthProperties;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.Gender;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Order(value = 10)
@RequiredArgsConstructor
public class RandomUsersBoot implements ApplicationRunner {
    private static final String symbols = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    private final AuthProperties authProperties;
    private final UserComponent userComponent;
    private final RoleComponent roleComponent;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Boolean.TRUE.equals(authProperties.getFillUsers())) {
            var roles = roleComponent.getAll();
            var userStatuses = UserStatusCode.values();
            var accountStatuses = AccountStatusCode.values();
            var modes = ModeType.values();
            var genders = Gender.values();
            var count = RandomUtils.nextInt(20, 100);

            List<UserEntity> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                var username = getRandomName();
                var user = UserEntity.builder()
                        .username(username)
                        .hashCode(passwordEncoder.encode(username))
                        .status(userStatuses[getRandomIndex(userStatuses.length)])
                        .roles(roles.subList(0, getRandomIndex(roles.size() - 1)))
                        .lastHashes(Collections.emptyList())
                        .account(Account.builder()
                                .nickname(username)
                                .email(username + "@mail.co")
                                .status(accountStatuses[getRandomIndex(accountStatuses.length)])
                                .mode(modes[getRandomIndex(modes.length)])
                                .gender(genders[getRandomIndex(genders.length)])
                                .firstName(getRandomName())
                                .lastName(getRandomName())
                                .build())
                        .build();
                list.add(user);
            }

            var res = userComponent.saveAll(list);
            log.info("Created {} random users", res.size());
        }
    }

    private int getRandomIndex(int length) {
        return RandomUtils.nextInt(0, length);
    }

    private String getRandomName() {
        return RandomStringUtils.random(RandomUtils.nextInt(4, 9), symbols);
    }
}
