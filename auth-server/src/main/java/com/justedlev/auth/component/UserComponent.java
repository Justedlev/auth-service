package com.justedlev.auth.component;

import com.justedlev.auth.component.base.CreateEntity;
import com.justedlev.auth.component.base.DeleteEntity;
import com.justedlev.auth.component.base.SaveEntity;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.request.UserRequest;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserComponent extends CreateEntity<UserRequest, UserEntity>, SaveEntity<UserEntity>,
        DeleteEntity<UserEntity> {

    List<UserEntity> getPage(UserFilter filter, PaginationRequest request);

    List<UserEntity> getByFilter(UserFilter filter);

    UserEntity updatePassword(String username, String password);

    Optional<UserEntity> getByUsername(String username);
}
