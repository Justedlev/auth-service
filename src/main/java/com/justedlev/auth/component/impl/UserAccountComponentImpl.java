package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.UserAccountComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAccountComponentImpl implements UserAccountComponent {
    private final AccountComponent accountComponent;
    private final UserComponent userComponent;

    @Override
    public UserEntity deleteUserByUsername(String username) {
        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();
        var user = userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, username)));
        accountComponent.delete(user.getAccount());

        return userComponent.delete(user);
    }

    @Override
    public Account deleteAccountByNickname(String nickname) {
        var filter = AccountFilter.builder()
                .nicknames(List.of(nickname))
                .build();
        var account = accountComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));
        userComponent.delete(account.getUser());

        return accountComponent.delete(account);
    }

    @Override
    public Account deleteAccountByEmail(String email) {
        var filter = AccountFilter.builder()
                .emails(List.of(email))
                .build();
        var account = accountComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, email)));
        userComponent.delete(account.getUser());

        return accountComponent.delete(account);
    }
}
