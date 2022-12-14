package com.justedlev.auth.controller;

import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.model.request.HistoryRequest;
import com.justedlev.auth.model.response.AccountHistoryResponse;
import com.justedlev.auth.model.response.UserHistoryResponse;
import com.justedlev.auth.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointConstant.HISTORY)
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping(value = EndpointConstant.ACCOUNT)
    public ResponseEntity<List<AccountHistoryResponse>> getAccounts(@Valid @RequestBody HistoryRequest request) {
        var response = historyService.getAccounts(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = EndpointConstant.USER)
    public ResponseEntity<List<UserHistoryResponse>> getUsers(@Valid @RequestBody HistoryRequest request) {
        var response = historyService.getUsers(request);

        return ResponseEntity.ok(response);
    }
}
