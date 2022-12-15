package com.justedlev.auth.component;

import java.util.List;

import com.justedlev.auth.component.command.AccountModeCommand;
import com.justedlev.auth.model.response.AccountResponse;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(AccountModeCommand command);
}
