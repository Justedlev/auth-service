package com.justedlev.auth.service;

import com.justedlev.auth.model.response.ReportResponse;

public interface UserAccountService {
    ReportResponse delete(String username);
}
