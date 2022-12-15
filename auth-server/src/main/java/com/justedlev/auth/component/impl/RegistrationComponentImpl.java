package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.component.MailComponent;
import com.justedlev.auth.component.RegistrationComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.component.command.SendEmailCommand;
import com.justedlev.auth.common.mapper.ReportMapper;
import com.justedlev.auth.common.validator.RegistrationValidator;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.RegistrationRequest;
import com.justedlev.auth.model.request.UserRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegistrationComponentImpl implements RegistrationComponent {
    private final UserComponent userComponent;
    private final MailComponent mailComponent;
    private final ReportMapper reportMapper;
    private final ModelMapper defaultMapper;
    private final AccountComponent accountComponent;
    private final RegistrationValidator registrationValidator;

    @Override
    @Transactional
    public ReportResponse registration(RegistrationRequest request) {
        registrationValidator.validate(request);
        var createAccountRequest = toAccountRequest(request);
        var createUserRequest = toUserRequest(request);
        var account = accountComponent.create(createAccountRequest);
        var user = createUser(createUserRequest, account);
        sendConfirmEmail(account, user);

        return reportMapper.toReport();
    }

    private void sendConfirmEmail(Account account, UserEntity user) {
        var cmd = SendEmailCommand.builder()
                .recipient(account.getEmail())
                .userName(user.getUsername())
                .activationCode(account.getActivationCode())
                .build();

        mailComponent.sendConfirmActivationMail(cmd);
    }

    private UserEntity createUser(UserRequest createUserRequest, Account account) {
        var user = userComponent.create(createUserRequest);
        user.setAccount(account);

        return userComponent.save(user);
    }

    private AccountRequest toAccountRequest(RegistrationRequest request) {
        return defaultMapper.map(request, AccountRequest.class);
    }

    private UserRequest toUserRequest(RegistrationRequest request) {
        var userRequest = defaultMapper.map(request, UserRequest.class);
        userRequest.setUsername(request.getNickname());

        return userRequest;
    }
}
