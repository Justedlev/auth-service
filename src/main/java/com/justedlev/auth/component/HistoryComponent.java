package com.justedlev.auth.component;

import com.justedlev.auth.model.request.HistoryRequest;
import com.justedlev.auth.model.response.AccountHistoryResponse;
import com.justedlev.auth.model.response.UserHistoryResponse;

import java.util.List;

public interface HistoryComponent {
    List<AccountHistoryResponse> getAccounts(HistoryRequest request);

    List<UserHistoryResponse> getUsers(HistoryRequest request);
}
