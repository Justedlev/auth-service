package com.justedlev.auth.controller;

import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.AccountResponse;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(EndpointConstant.ACCOUNT)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping(value = EndpointConstant.PAGE)
    public ResponseEntity<PageResponse<List<AccountResponse>>> getPage(@Valid @RequestBody PaginationRequest request) {
        return ResponseEntity.ok(accountService.getPage(request));
    }

//    @GetMapping(value = AccountEndpoint.EMAIL)
//    public ResponseEntity<AccountResponse> getAccountByEmail(@PathVariable String email) {
//        var responseBody = accountService.getByEmail(email);
//
//        return ResponseEntity.ok(responseBody);
//    }

    @GetMapping(value = EndpointConstant.NICKNAME)
    public ResponseEntity<AccountResponse> getAccountByNickname(@PathVariable
                                                                @Valid
                                                                @NotBlank(message = "Nickname cannot be empty.")
                                                                String nickname) {
        return ResponseEntity.ok(accountService.getByNickname(nickname));
    }

    @PutMapping(value = EndpointConstant.NICKNAME_UPDATE)
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable
                                                         @Valid
                                                         @NotBlank(message = "Nickname cannot be empty.")
                                                         String nickname,
                                                         @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.update(nickname, request));
    }

    @PostMapping(value = EndpointConstant.NICKNAME_UPDATE_PHOTO, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AccountResponse> updateAccountPhoto(@PathVariable
                                                              @Valid
                                                              @NotBlank(message = "Nickname cannot be empty.")
                                                              String nickname,
                                                              @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updatePhoto(nickname, file));
    }

    @GetMapping(value = EndpointConstant.CONFIRM_CODE)
    public ResponseEntity<ReportResponse> confirm(@Valid
                                                  @NotEmpty(message = "Confirm code cannot be empty.")
                                                  @PathVariable
                                                  String code) {
        return ResponseEntity.ok(accountService.confirm(code));
    }
}
