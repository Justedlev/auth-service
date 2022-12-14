package com.justedlev.auth.repository.custom.impl;

import com.justedlev.auth.repository.custom.AccountCustomRepository;
import com.justedlev.auth.repository.custom.filter.AccountFilter;
import com.justedlev.auth.repository.entity.Account;
import com.justedlev.auth.util.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountCustomRepositoryImpl implements AccountCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Account> findByFilter(@NonNull AccountFilter filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Account.class);
        var root = cq.from(Account.class);
        root.fetch("user", JoinType.LEFT)
                .fetch("roles", JoinType.LEFT);
        var predicates = applyFilter(filter, cb, root);

        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(Predicate[]::new));
        }

        return em.createQuery(cq)
                .getResultList();
    }

    @Override
    public List<Account> findByFilter(@NonNull AccountFilter filter, @NonNull Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(String.class);
        var root = cq.from(Account.class);
        var predicates = applyFilter(filter, cb, root);

        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(Predicate[]::new));
        }

        cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
        var ids = em.createQuery(cq.select(root.get("id")))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        var idFilter = AccountFilter.builder()
                .ids(ids)
                .build();

        return this.findByFilter(idFilter);
    }

    private List<Predicate> applyFilter(AccountFilter filter, CriteriaBuilder cb, Root<Account> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get("id").in(filter.getIds()));
        }

        if (CollectionUtils.isNotEmpty(filter.getEmails())) {
            predicates.add(cb.lower(root.get("email")).in(Converter.toLowerCase(filter.getEmails())));
        }

        if (CollectionUtils.isNotEmpty(filter.getNicknames())) {
            predicates.add(cb.lower(root.get("nickname")).in(Converter.toLowerCase(filter.getNicknames())));
        }

        if (CollectionUtils.isNotEmpty(filter.getModes())) {
            predicates.add(root.get("mode").in(filter.getModes()));
        }

        if (ObjectUtils.isNotEmpty(filter.getModeAtFrom())) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("modeAt"), filter.getModeAtFrom()));
        }

        if (ObjectUtils.isNotEmpty(filter.getModeAtTo())) {
            predicates.add(cb.lessThanOrEqualTo(root.get("modeAt"), filter.getModeAtTo()));
        }

        if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
            predicates.add(root.get("status").in(filter.getStatuses()));
        }

        if (CollectionUtils.isNotEmpty(filter.getActivationCodes())) {
            predicates.add(root.get("activationCode").in(filter.getActivationCodes()));
        }

        return predicates;
    }
}
