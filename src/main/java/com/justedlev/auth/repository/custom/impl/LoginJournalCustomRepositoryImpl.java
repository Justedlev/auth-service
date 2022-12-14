package com.justedlev.auth.repository.custom.impl;

import com.justedlev.auth.repository.custom.filter.LoginJournalFilter;
import com.justedlev.auth.repository.custom.LoginJournalCustomRepository;
import com.justedlev.auth.repository.entity.LoginJournal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoginJournalCustomRepositoryImpl implements LoginJournalCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<LoginJournal> findByFilter(LoginJournalFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LoginJournal> cq = cb.createQuery(LoginJournal.class);
        Root<LoginJournal> entityRoot = cq.from(LoginJournal.class);
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getUserId())) {
            predicates.add(entityRoot.get("createdBy").in(filter.getUserId()));
        }

        if (ObjectUtils.isNotEmpty(filter.getFromLoginDate()) && ObjectUtils.isNotEmpty(filter.getToLoginDate())) {
            predicates.add(cb.between(entityRoot.get("createdAt"), filter.getFromLoginDate(), filter.getToLoginDate()));
        }

        cq.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getResultList();
    }
}
