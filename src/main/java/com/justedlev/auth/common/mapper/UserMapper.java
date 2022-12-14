package com.justedlev.auth.common.mapper;

import com.justedlev.auth.component.base.EntityMapper;
import com.justedlev.auth.component.base.ResponseMapper;
import com.justedlev.auth.model.request.UserRequest;
import com.justedlev.auth.model.response.UserResponse;
import com.justedlev.auth.repository.entity.UserEntity;

public interface UserMapper extends ResponseMapper<UserEntity, UserResponse>, EntityMapper<UserRequest, UserEntity> {
}
