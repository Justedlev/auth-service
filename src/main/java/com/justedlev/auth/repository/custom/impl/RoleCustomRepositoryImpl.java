package com.justedlev.auth.repository.custom.impl;

import com.justedlev.auth.repository.custom.filter.RoleFilter;
import com.justedlev.auth.repository.custom.RoleCustomRepository;
import com.justedlev.auth.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
public class RoleCustomRepositoryImpl implements RoleCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Role> findByFilter(RoleFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> entityRoot = cq.from(Role.class);

        if(ObjectUtils.isNotEmpty(filter)) {
            List<Predicate> predicates = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(filter.getIds())) {
                predicates.add(entityRoot.get("id").in(filter.getIds()));
            }

            if (CollectionUtils.isNotEmpty(filter.getTypes())) {
                predicates.add(entityRoot.get("type").in(filter.getTypes()));
            }

            if (CollectionUtils.isNotEmpty(filter.getGroups())) {
                predicates.add(entityRoot.get("group").in(filter.getGroups()));
            }

            if (StringUtils.isNotBlank(filter.getAbout())) {
                String q = "%" + filter.getAbout() + "%";
                predicates.add(cb.like(entityRoot.get("about"), q));
            }

            cq.where(predicates.toArray(Predicate[]::new));
        }

        return em.createQuery(cq).getResultList();
    }
}
