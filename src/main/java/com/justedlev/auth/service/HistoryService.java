package com.justedlev.auth.service;

import com.justedlev.auth.model.request.HistoryRequest;
import com.justedlev.auth.model.response.AccountHistoryResponse;
import com.justedlev.auth.model.response.UserHistoryResponse;

import java.util.List;

public interface HistoryService {
    List<AccountHistoryResponse> getAccounts(HistoryRequest request);

    List<UserHistoryResponse> getUsers(HistoryRequest request);
}
