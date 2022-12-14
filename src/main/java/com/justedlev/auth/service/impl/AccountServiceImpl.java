package com.justedlev.auth.service.impl;

import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.common.mapper.ReportMapper;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.PageCounterComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountComponent accountComponent;
    private final AccountMapper accountMapper;
    private final ReportMapper reportMapper;
    private final PageCounterComponent pageCounterComponent;

    @Override
    public PageResponse<List<AccountResponse>> getPage(PaginationRequest request) {
        var pageCount = pageCounterComponent.accountPageCount(request.getSize());

        if (pageCount < request.getPage()) {
            throw new IllegalArgumentException(String.format("Maximum pages is %s", pageCount));
        }

        var accounts = accountComponent.getPage(new AccountFilter(), request);
        var data = accountMapper.mapToResponse(accounts);

        return PageResponse.<List<AccountResponse>>builder()
                .page(request.getPage())
                .maxPages(pageCount)
                .data(data)
                .build();
    }

    @Override
    public AccountResponse getByEmail(String email) {
        var filter = AccountFilter.builder()
                .emails(Set.of(email))
                .build();
        var account = accountComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, email)));

        return accountMapper.mapToResponse(account);
    }

    @Override
    public AccountResponse getByNickname(String nickname) {
        var filter = AccountFilter.builder()
                .nicknames(Set.of(nickname))
                .build();
        var account = accountComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return accountMapper.mapToResponse(account);
    }

    @Override
    public ReportResponse confirm(String code) {
        var account = accountComponent.confirm(code);

        return reportMapper.toReport(String.format("User %s confirmed account", account.getNickname()));
    }

    @Override
    public AccountResponse update(String nickname, AccountRequest request) {
        var account = accountComponent.update(nickname, request);

        return accountMapper.mapToResponse(account);
    }

    @Override
    public AccountResponse updatePhoto(String nickname, MultipartFile photo) {
        var account = accountComponent.update(nickname, photo);
        var response = accountMapper.mapToResponse(account);
        response.setPhotoUrl(account.getPhoto().getUrl());

        return response;
    }
}
