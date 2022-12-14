package com.justedlev.auth.repository.custom;

import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCustomRepository {
    List<UserEntity> findByFilter(UserFilter filter);

    List<UserEntity> findByFilter(UserFilter filter, Pageable pageable);
}
