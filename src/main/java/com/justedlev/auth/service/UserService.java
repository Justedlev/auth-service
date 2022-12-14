package com.justedlev.auth.service;

import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    PageResponse<List<UserResponse>> getPage(PaginationRequest request);

    UserResponse getByUsername(String username);
}
