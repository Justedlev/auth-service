package com.justedlev.auth.controller;

import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.response.PageResponse;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.UserResponse;
import com.justedlev.auth.service.UserAccountService;
import com.justedlev.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(EndpointConstant.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAccountService userAccountService;

    @PostMapping(value = EndpointConstant.PAGE)
    public ResponseEntity<PageResponse<List<UserResponse>>> getUsers(@Valid @RequestBody PaginationRequest request) {
        var response = userService.getPage(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = EndpointConstant.USERNAME)
    public ResponseEntity<UserResponse> getUser(@PathVariable
                                                @Valid
                                                @NotBlank(message = "Username cannot be empty.")
                                                String username) {
        var response = userService.getByUsername(username);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = EndpointConstant.USERNAME_DELETE)
    public ResponseEntity<ReportResponse> delete(@PathVariable
                                                 @Valid
                                                 @NotBlank(message = "Username cannot be empty.")
                                                 String username) {
        var response = userAccountService.delete(username);

        return ResponseEntity.ok(response);
    }
}
