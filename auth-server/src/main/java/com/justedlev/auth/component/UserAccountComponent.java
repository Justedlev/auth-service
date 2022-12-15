package com.justedlev.auth.component;

import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;

public interface UserAccountComponent {
    UserEntity deleteUserByUsername(String username);

    Account deleteAccountByNickname(String nickname);

    Account deleteAccountByEmail(String email);
}
