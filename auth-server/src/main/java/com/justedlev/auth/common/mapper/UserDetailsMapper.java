package com.justedlev.auth.common.mapper;

import com.justedlev.auth.repository.entity.UserEntity;

public interface UserDetailsMapper {
    org.springframework.security.core.userdetails.User map(UserEntity user);
}
