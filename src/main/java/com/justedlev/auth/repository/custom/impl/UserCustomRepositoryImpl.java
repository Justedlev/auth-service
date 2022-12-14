package com.justedlev.auth.repository.custom.impl;

import com.justedlev.auth.repository.custom.UserCustomRepository;
import com.justedlev.auth.repository.custom.filter.UserFilter;
import com.justedlev.auth.repository.entity.UserEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<UserEntity> findByFilter(@Nullable UserFilter filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(UserEntity.class);
        var root = cq.from(UserEntity.class);
        root.fetch("roles", JoinType.LEFT);
        root.fetch("account", JoinType.LEFT);
        var predicates = applyFilter(filter, root);

        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(Predicate[]::new));
        }

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<UserEntity> findByFilter(@Nullable UserFilter filter, @NonNull Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<UserEntity> root = cq.from(UserEntity.class);
        List<Predicate> predicates = applyFilter(filter, root);

        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(Predicate[]::new));
        }

        cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));

        var ids = em.createQuery(cq.select(root.get("id")))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        var idFilter = UserFilter.builder()
                .ids(ids)
                .build();

        return this.findByFilter(idFilter);
    }

    private List<Predicate> applyFilter(UserFilter filter, Root<UserEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(filter)) {
            if (CollectionUtils.isNotEmpty(filter.getIds())) {
                predicates.add(root.get("id").in(filter.getIds()));
            }

            if (CollectionUtils.isNotEmpty(filter.getUsernames())) {
                predicates.add(root.get("username").in(filter.getUsernames()));
            }

            if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
                predicates.add(root.get("status").in(filter.getStatuses()));
            }
        }

        return predicates;
    }
}
