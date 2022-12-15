package com.justedlev.auth.service.impl;

import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.common.mapper.ReportMapper;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.AccountModeComponent;
import com.justedlev.auth.component.PageCounterComponent;
import com.justedlev.auth.component.command.AccountModeCommand;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.properties.AuthProperties;
import com.justedlev.auth.properties.TokenAccessProperties;
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
    private final TokenAccessProperties tokenAccessProperties;
    private final AuthProperties properties;
    private final AccountComponent accountComponent;
    private final AccountMapper accountMapper;
    private final ReportMapper reportMapper;
    private final PageCounterComponent pageCounterComponent;
    private final AccountModeComponent accountModeComponent;

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

    @Override
    public List<AccountResponse> sleepMode() {
        var command = AccountModeCommand.builder()
                .fromModes(Set.of(ModeType.HIDDEN, ModeType.ONLINE))
                .duration(properties.getActivityTime())
                .toMode(ModeType.SLEEP)
                .build();

        return accountModeComponent.updateMode(command);
    }

    @Override
    public List<AccountResponse> offlineMode() {
        var command = AccountModeCommand.builder()
                .fromModes(Set.of(ModeType.HIDDEN, ModeType.ONLINE, ModeType.SLEEP))
                .duration(tokenAccessProperties.getExpirationTime())
                .toMode(ModeType.OFFLINE)
                .build();

        return accountModeComponent.updateMode(command);
    }
}
