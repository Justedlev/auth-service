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
        return (int) Math.ceil((double) accountRepository.count() / size);
    }

    @Override
    public int userPageCount(int size) {
        return (int) Math.ceil((double) userRepository.count() / size);
    }

    @Override
    public int rolePageCount(int size) {
        return (int) Math.ceil((double) roleRepository.count() / size);
    }
}
