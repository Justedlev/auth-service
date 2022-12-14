package com.justedlev.auth.repository.specification;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.naming.OperationNotSupportedException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomSpecificationBuilder<E> {

    private Specification<E> specification;

    public CustomSpecificationBuilder() {
    }

    public CustomSpecificationBuilder(@NonNull Criteria criteria) {
        this.specification = new EntitySpecification(criteria);
    }

    public CustomSpecificationBuilder(@NonNull String attribute, @NonNull QueryOperator operator, @NonNull Object from, @NonNull Object to) {
        this(new Criteria(attribute, operator, from, to));
    }

    public CustomSpecificationBuilder(@NonNull String attribute, @NonNull QueryOperator operator, @NonNull Object value) {
        this(new Criteria(attribute, operator, value));
    }

    public CustomSpecificationBuilder(@NonNull String attribute, @NonNull QueryOperator operator) {
        this(new Criteria(attribute, operator));
    }

    public CustomSpecificationBuilder<E> and(@NonNull Criteria criteria) {
        if (ObjectUtils.isEmpty(this.specification)) {
            this.specification = new EntitySpecification(criteria);
        } else {
            this.and(new EntitySpecification(criteria));
        }
        return this;
    }

    public CustomSpecificationBuilder<E> and(@NonNull String attribute, @NonNull QueryOperator operator, @NonNull Object value) {

        this.and(new Criteria(attribute, operator, value));
        return this;
    }

    public CustomSpecificationBuilder<E> andIsNull(@NonNull String attribute) {
        this.and(new Criteria(attribute, QueryOperator.IS_NULL));
        return this;
    }

    public CustomSpecificationBuilder<E> andIsNotNull(@NonNull String attribute) {
        this.and(new Criteria(attribute, QueryOperator.NOT_NULL));
        return this;
    }

    public CustomSpecificationBuilder<E> andBetween(@NonNull String attribute, @NonNull Object from, @NonNull Object to) {
        this.and(new Criteria(attribute, QueryOperator.BETWEEN, from, to));
        return this;
    }

    public CustomSpecificationBuilder<E> or(@NonNull Criteria criteria1, @NonNull Criteria criteria2) {
        EntitySpecification entitySpecification1 = new EntitySpecification(criteria1);
        EntitySpecification entitySpecification2 = new EntitySpecification(criteria2);
        this.or(entitySpecification1, entitySpecification2);
        return this;
    }

    public CustomSpecificationBuilder<E> or(@NonNull String attribute1, @NonNull QueryOperator operator1, @NonNull Object value1,
                                            @NonNull String attribute2, @NonNull QueryOperator operator2, @NonNull Object value2) {
        Criteria criteria1 = new Criteria(attribute1, operator1, value1);
        Criteria criteria2 = new Criteria(attribute2, operator2, value2);
        this.or(criteria1, criteria2);
        return this;
    }

    public CustomSpecificationBuilder<E> orIsNull(@NonNull String attribute1, @NonNull String attribute2) {
        Criteria criteria1 = new Criteria(attribute1,QueryOperator.NOT_NULL);
        Criteria criteria2 = new Criteria(attribute2,QueryOperator.NOT_NULL);
        this.or(criteria1, criteria2);
        return this;
    }

    public CustomSpecificationBuilder<E> orIsNotNull(@NonNull String attribute1, @NonNull String attribute2) {
        Criteria criteria1 = new Criteria(attribute1, QueryOperator.NOT_NULL);
        Criteria criteria2 = new Criteria(attribute2, QueryOperator.NOT_NULL);
        this.or(criteria1, criteria2);
        return this;
    }

    public CustomSpecificationBuilder<E> orBetween(@NonNull String attribute1, @NonNull Object from1, @NonNull Object to1,
                                                   @NonNull String attribute2, @NonNull Object from2, @NonNull Object to2) {
        Criteria criteria1 = new Criteria(attribute1, QueryOperator.BETWEEN, from1, to1);
        Criteria criteria2 = new Criteria(attribute2, QueryOperator.BETWEEN, from2, to2);
        this.or(criteria1, criteria2);
        return this;
    }

    public Specification<E> build() {
        return specification;
    }

    private void and(@NonNull Specification<E> specification) {
        if (ObjectUtils.isEmpty(this.specification)) {
            this.specification = specification;
        } else {
            this.specification = this.specification.and(specification);
        }
    }

    private void or(@NonNull Specification<E> specification1,
                    @NonNull Specification<E> specification2) {

        this.and(Specification.where(specification1).or(specification2));
    }

    private class EntitySpecification implements Specification<E> {

        private final Criteria criteria;

        private EntitySpecification(Criteria criteria) {
            this.criteria = criteria;
        }

        @Override
        public Predicate toPredicate(@NonNull Root<E> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
            try {
                return DynamicCriteriaPredicate.toPredicate(root, criteria, query, builder);
            } catch (OperationNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

}
