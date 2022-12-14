package com.justedlev.auth.repository;

import com.justedlev.auth.repository.entity.Role;
import com.justedlev.auth.repository.custom.RoleCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleCustomRepository {
}
