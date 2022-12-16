package com.justedlev.auth.component;

import java.util.List;

import com.justedlev.auth.model.request.UpdateAccountModeRequest;
import com.justedlev.auth.model.response.AccountResponse;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
