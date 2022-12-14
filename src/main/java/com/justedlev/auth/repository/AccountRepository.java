package com.justedlev.auth.repository;

import com.justedlev.auth.repository.custom.AccountCustomRepository;
import com.justedlev.auth.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, AccountCustomRepository {
    boolean existsByNickname(String nickname);
}
