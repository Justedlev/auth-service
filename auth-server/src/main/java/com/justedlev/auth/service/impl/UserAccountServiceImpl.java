package com.justedlev.auth.service.impl;

import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.service.UserAccountService;
import com.justedlev.auth.component.UserAccountComponent;
import com.justedlev.auth.common.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final ReportMapper reportMapper;
    private final UserAccountComponent userAccountComponent;

    @Override
    public ReportResponse delete(String username) {
        var user = userAccountComponent.deleteUserByUsername(username);

        return reportMapper.toReport(String.format("User %s has been deleted", user.getUsername()));
    }
}
