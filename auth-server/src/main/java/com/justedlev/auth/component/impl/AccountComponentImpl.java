package com.justedlev.auth.component.impl;

import com.justedlev.auth.common.converter.PhoneNumberConverter;
import com.justedlev.auth.common.mapper.AccountMapper;
import com.justedlev.auth.component.AccountComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.model.Photo;
import com.justedlev.auth.model.request.AccountRequest;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.repository.AccountRepository;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.storage.client.StorageFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AccountComponentImpl implements AccountComponent {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PhoneNumberConverter phoneNumberConverter;
    private final StorageFeignClient storageFeignClient;
    private final ModelMapper defaultMapper;

    @Override
    public List<Account> getPage(AccountFilter filter, PaginationRequest request) {
        var page = PageRequest.of(request.getPage() - 1, request.getSize(),
                Sort.Direction.DESC, "createdAt");

        return accountRepository.findByFilter(filter, page);
    }

    @Override
    public List<Account> getByFilter(AccountFilter filter) {
        return Optional.ofNullable(filter)
                .map(accountRepository::findByFilter)
                .orElse(Collections.emptyList());
    }

    @Override
    public Account confirm(String activationCode) {
        validateActivationCode(activationCode);
        var filter = AccountFilter.builder()
                .activationCodes(Set.of(activationCode))
                .statuses(Set.of(AccountStatusCode.UNCONFIRMED))
                .build();
        var account = accountRepository.findByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Already activated"));
        account.setStatus(AccountStatusCode.ACTUAL);

        return save(account);
    }

    @Override
    public Account update(String nickname, AccountRequest request) {
        if (isNicknameTaken(request.getNickname())) {
            throw new EntityExistsException(String.format(ExceptionConstant.NICKNAME_TAKEN, nickname));
        }

        var filter = AccountFilter.builder()
                .nicknames(Set.of(nickname))
                .build();
        var accounts = getByFilter(filter);
        var account = accounts.stream()
                .filter(current -> current.getNickname().equalsIgnoreCase(nickname))
                .max(Comparator.comparing(Account::getCreatedAt))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return update(account, request);
    }

    @Override
    public Account deactivate(String nickname) {
        return null;
    }

    @Override
    public Account activate(String nickname) {
        return null;
    }

    @Override
    public Account create(AccountRequest request) {
        return getByEmail(request.getEmail())
                .or(() -> getByNickname(request.getNickname()))
                .filter(current -> !current.getStatus().equals(AccountStatusCode.DELETED))
                .orElseGet(() -> accountMapper.mapToEntity(request));
    }

    @Override
    public Account save(Account entity) {
        return Optional.ofNullable(entity)
                .map(accountRepository::save)
                .orElse(null);
    }

    @Override
    public List<Account> saveAll(List<Account> entities) {
        return Optional.ofNullable(entities)
                .filter(CollectionUtils::isNotEmpty)
                .map(accountRepository::saveAll)
                .orElse(Collections.emptyList());
    }

    @Override
    public Account update(Account entity, AccountRequest request) {
        if (StringUtils.isNotBlank(request.getNickname())) {
            entity.setNickname(request.getNickname());
        }

        if (ObjectUtils.isNotEmpty(request.getBirthDate())) {
            var birthDate = Timestamp.valueOf(request.getBirthDate());
            entity.setBirthDate(birthDate);
        }

        if (StringUtils.isNotBlank(request.getFirstName())) {
            entity.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotBlank(request.getLastName())) {
            entity.setLastName(request.getLastName());
        }

        if (ObjectUtils.isNotEmpty(request.getGender())) {
            entity.setGender(request.getGender());
        }

        if (StringUtils.isNotBlank(request.getPhoneNumber())) {
            var phone = phoneNumberConverter.convert(request.getPhoneNumber());
            entity.setPhoneNumberInfo(phone);
        }

        return save(entity);
    }

    @Override
    public Account delete(Account entity) {
        if (entity.getStatus().equals(AccountStatusCode.DELETED)) {
            throw new IllegalArgumentException(
                    String.format(ExceptionConstant.ACCOUNT_ALREADY_DELETED, entity.getNickname()));
        }

        entity.setStatus(AccountStatusCode.DELETED);
        entity.setMode(ModeType.OFFLINE);

        return save(entity);
    }

    @Override
    public Optional<Account> getByNickname(String nickname) {
        return Optional.ofNullable(nickname)
                .filter(StringUtils::isNotBlank)
                .map(Set::of)
                .map(current -> AccountFilter.builder()
                        .nicknames(current)
                        .build())
                .map(this::getByFilter)
                .stream()
                .flatMap(Collection::stream)
                .findFirst();
    }

    @Override
    @SneakyThrows
    public Account update(String nickname, MultipartFile photo) {
        var account = getByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));
        Optional.ofNullable(account.getPhoto())
                .map(Photo::getName)
                .ifPresent(storageFeignClient::delete);
        storageFeignClient.upload(List.of(photo))
                .stream()
                .findFirst()
                .ifPresent(current -> account.setPhoto(defaultMapper.map(current, Photo.class)));

        return save(account);
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return Optional.ofNullable(email)
                .filter(StringUtils::isNotBlank)
                .map(Set::of)
                .map(current -> AccountFilter.builder()
                        .emails(current)
                        .build())
                .map(this::getByFilter)
                .stream()
                .flatMap(Collection::stream)
                .findFirst();
    }

    private void validateActivationCode(String activationCode) {
        if (StringUtils.isBlank(activationCode))
            throw new IllegalArgumentException("Code not valid");
    }

    private boolean isNicknameTaken(String nickname) {
        if (StringUtils.isBlank(nickname)) {
            return false;
        }

        var filter = AccountFilter.builder()
                .nicknames(Set.of(nickname))
                .build();

        return CollectionUtils.isNotEmpty(getByFilter(filter));
    }
}
