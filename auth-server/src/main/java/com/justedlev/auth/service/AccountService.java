package com.justedlev.auth.service;

import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    PageResponse<List<AccountResponse>> getPage(PaginationRequest request);

    AccountResponse getByEmail(String email);

    AccountResponse getByNickname(String nickname);

    ReportResponse confirm(String code);

    AccountResponse update(String nickname, AccountRequest request);

    AccountResponse updatePhoto(String nickname, MultipartFile photo);

    List<AccountResponse> sleepMode();

    List<AccountResponse> offlineMode();
}
