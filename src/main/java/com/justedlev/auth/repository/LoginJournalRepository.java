package com.justedlev.auth.repository;

import com.justedlev.auth.repository.custom.LoginJournalCustomRepository;
import com.justedlev.auth.repository.entity.LoginJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginJournalRepository extends JpaRepository<LoginJournal, Long>, LoginJournalCustomRepository {
}
