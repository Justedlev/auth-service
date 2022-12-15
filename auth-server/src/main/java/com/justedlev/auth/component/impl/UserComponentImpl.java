package com.justedlev.auth.component.impl;

import com.justedlev.auth.component.RoleComponent;
import com.justedlev.auth.component.UserComponent;
import com.justedlev.auth.constant.ExceptionConstant;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.model.request.PaginationRequest;
import com.justedlev.auth.model.request.UserRequest;
import com.justedlev.auth.repository.UserRepository;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleComponent roleComponent;

    @Override
    public List<UserEntity> getPage(UserFilter filter, PaginationRequest request) {
        var page = PageRequest.of(request.getPage() - 1, request.getSize())
                .withSort(Sort.Direction.DESC, "createdAt");

        return userRepository.findByFilter(filter, page);
    }

    @Override
    public List<UserEntity> getByFilter(UserFilter filter) {
        return Optional.ofNullable(filter)
                .map(userRepository::findByFilter)
                .orElse(Collections.emptyList());
    }

    @Override
    public UserEntity updatePassword(String username, String password) {
        return null;
    }

    @Override
    public Optional<UserEntity> getByUsername(String username) {
        return Optional.ofNullable(username)
                .filter(StringUtils::isNotBlank)
                .map(Set::of)
                .map(current -> UserFilter.builder()
                        .usernames(current)
                        .build())
                .map(this::getByFilter)
                .stream()
                .flatMap(Collection::stream)
                .findFirst();
    }

    @Override
    public UserEntity create(UserRequest request) {
        var statuses = List.of(
                UserStatusCode.ACTUAL,
                UserStatusCode.DEACTIVATED
        );
        var filter = UserFilter.builder()
                .statuses(statuses)
                .build();
        var existsUser = getByFilter(filter)
                .stream()
                .findFirst();

        if (existsUser.isPresent()) {
            return existsUser.get();
        }

        var role = this.roleComponent.get(request.getRoleType())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.ROLE_NOT_EXISTS, request.getRoleType())));
        var hash = passwordEncoder.encode(request.getPassword());

        return UserEntity.builder()
                .username(request.getUsername())
                .hashCode(hash)
                .roles(List.of(role))
                .build();
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(userRepository::save)
                .orElse(null);
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> entities) {
        return Optional.ofNullable(entities)
                .filter(CollectionUtils::isNotEmpty)
                .map(userRepository::saveAll)
                .orElse(Collections.emptyList());
    }

    @Override
    public UserEntity delete(UserEntity entity) {
        if (entity.getStatus().equals(UserStatusCode.DELETED)) {
            throw new IllegalArgumentException(
                    String.format(ExceptionConstant.USER_ALREADY_DELETED, entity.getUsername()));
        }

        entity.setStatus(UserStatusCode.DELETED);

        return this.save(entity);
    }
}
