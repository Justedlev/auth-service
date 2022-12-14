package com.justedlev.auth.service.impl;

import com.justedlev.auth.common.mapper.UserDetailsMapper;
import com.justedlev.auth.common.mapper.UserMapper;
import com.justedlev.auth.component.PageCounterComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.UserResponse;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final UserMapper userResponseMapper;
    private final UserDetailsMapper userDetailsMapper;
    private final PageCounterComponent pageCounterComponent;

    @Override
    public PageResponse<List<UserResponse>> getPage(PaginationRequest request) {
        var pageCount = pageCounterComponent.userPageCount(request.getSize());

        if (pageCount < request.getPage()) {
            throw new IllegalArgumentException(String.format("Maximum pages is %s", pageCount));
        }

        var users = userComponent.getPage(null, request);
        var data = userResponseMapper.mapToResponse(users);

        return PageResponse.<List<UserResponse>>builder()
                .page(request.getPage())
                .maxPages(pageCount)
                .data(data)
                .build();
    }

    @Override
    public UserResponse getByUsername(String username) {
        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();

        return userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .map(userResponseMapper::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var filter = UserFilter.builder()
                .usernames(List.of(username))
                .build();

        return userComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .map(userDetailsMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, username)));
    }
}
