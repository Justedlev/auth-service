package com.justedlev.auth.repository.custom;

import com.justedlev.auth.repository.custom.filter.RoleFilter;
import com.justedlev.auth.repository.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleCustomRepository {
    List<Role> findByFilter(RoleFilter filter);
}
