package com.justedlev.auth.component.impl;

import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.common.mapper.UserMapper;
import com.justedlev.auth.component.HistoryComponent;
import com.justedlev.auth.model.request.HistoryRequest;
import com.justedlev.auth.model.response.AccountHistoryResponse;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.model.response.UserHistoryResponse;
import com.justedlev.auth.model.response.UserResponse;
import com.justedlev.auth.repository.AccountRepository;
import com.justedlev.auth.repository.UserRepository;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoryComponentImpl implements HistoryComponent {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    @Override
    public List<AccountHistoryResponse> getAccounts(HistoryRequest request) {
        var filter = AccountFilter.builder()
                .emails(request.getEmails())
                .build();
        var page = PageRequest.of(request.getPageRequest().getPage() - 1,
                request.getPageRequest().getSize(), Sort.Direction.DESC, "createdAt");
        var accounts = accountRepository.findByFilter(filter, page)
                .parallelStream()
                .map(accountMapper::mapToResponse)
                .collect(Collectors.groupingBy(AccountResponse::getEmail));

        return accounts.entrySet().parallelStream()
                .map(current -> AccountHistoryResponse.builder()
                        .email(current.getKey())
                        .accounts(current.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserHistoryResponse> getUsers(HistoryRequest request) {
        var filter = UserFilter.builder()
                .usernames(request.getUsernames())
                .build();
        var page = PageRequest.of(request.getPageRequest().getPage() - 1,
                request.getPageRequest().getSize(), Sort.Direction.DESC, "createdAt");
        var users = userRepository.findByFilter(filter, page)
                .parallelStream()
                .map(userMapper::mapToResponse)
                .collect(Collectors.groupingBy(UserResponse::getUsername));

        return users.entrySet()
                .parallelStream()
                .map(current -> UserHistoryResponse.builder()
                        .username(current.getKey())
                        .users(current.getValue())
                        .build())
                .toList();
    }
}
