package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.PageCounterComponent;
import com.justedlev.auth.repository.AccountRepository;
import com.justedlev.auth.repository.RoleRepository;
import com.justedlev.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageCounterComponentImpl implements PageCounterComponent {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public int accountPageCount(int size) {
        return (int) Math.ceilDiv(accountRepository.count(), size);
    }

    @Override
    public int userPageCount(int size) {
        return (int) Math.ceilDiv(userRepository.count(), size);
    }

    @Override
    public int rolePageCount(int size) {
        return (int) Math.ceilDiv(roleRepository.count(), size);
    }
}
