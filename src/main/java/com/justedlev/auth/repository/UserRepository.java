package com.justedlev.auth.repository;

import com.justedlev.auth.repository.custom.UserCustomRepository;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, UserCustomRepository {
    List<UserEntity> findAllByRolesTypeIn(@NonNull Collection<String> roleType);

    @EntityGraph("UserEntity.account-roles")
    List<UserEntity> findAllByUsername(String username);
}
