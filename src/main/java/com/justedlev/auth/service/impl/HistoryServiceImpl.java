package com.justedlev.auth.service.impl;

import com.justedlev.auth.component.HistoryComponent;
import com.justedlev.auth.model.request.HistoryRequest;
import com.justedlev.auth.model.response.AccountHistoryResponse;
import com.justedlev.auth.model.response.UserHistoryResponse;
import com.justedlev.auth.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryComponent historyComponent;

    @Override
    public List<AccountHistoryResponse> getAccounts(HistoryRequest request) {
        return historyComponent.getAccounts(request);
    }

    @Override
    public List<UserHistoryResponse> getUsers(HistoryRequest request) {
        return historyComponent.getUsers(request);
    }
}
