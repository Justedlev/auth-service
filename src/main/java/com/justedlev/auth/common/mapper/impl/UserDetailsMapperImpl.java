package com.justedlev.auth.common.mapper.impl;

import com.justedlev.auth.repository.entity.Role;
import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.common.mapper.UserDetailsMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {
    @Override
    public User map(UserEntity user) {
        String[] roles = user.getRoles().stream()
                .map(Role::getType)
                .map(current -> "ROLE_" + current)
                .toArray(String[]::new);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

        return new User(user.getUsername(), user.getHashCode(), authorities);
    }
}
