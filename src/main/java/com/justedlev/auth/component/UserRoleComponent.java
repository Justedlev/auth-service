package com.justedlev.auth.component;

import com.justedlev.auth.repository.entity.UserEntity;
import com.justedlev.auth.repository.entity.Role;

import java.util.List;
import java.util.Set;

public interface UserRoleComponent {

    List<Role> getRoles(String username);

    UserEntity addRoles(String username, Set<String> roleTypes);

    UserEntity removeRoles(String username, Set<String> roleTypes);
}
