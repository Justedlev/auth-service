package com.justedlev.auth.repository.specification;

import lombok.NonNull;

import javax.naming.OperationNotSupportedException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Date;

public class DynamicCriteriaPredicate {

    private DynamicCriteriaPredicate() {
        throw new IllegalStateException("Utility class");
    }

    public static <E> Predicate toPredicate(@NonNull Root<E> root, @NonNull Criteria criteria,
                                            @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder)
            throws OperationNotSupportedException {
        String attributeName = criteria.getAttributeName();
        Object first = criteria.getFirst();
        Object second = criteria.getSecond();
        switch (criteria.getOperator()) {
            case EQUAL:
                return builder.equal(root.get(attributeName), first.toString());
            case GREATER_THAN, AFTER:
                return builder.greaterThan(root.get(attributeName), first.toString());
            case LESS_THAN, BEFORE:
                return builder.lessThan(root.get(attributeName), first.toString());
            case GREATER_THAN_OR_EQUAL:
                return builder.greaterThanOrEqualTo(root.get(attributeName), first.toString());
            case LESS_THAN_OR_EQUAL:
                return builder.lessThanOrEqualTo(root.get(attributeName), first.toString());
            case LIKE:
                return builder.like(root.get(attributeName), "%" + first.toString() + "%");
            case NOT_LIKE:
                return builder.notLike(root.get(attributeName), "%" + first.toString() + "%");
            case IN: {
                if (first instanceof Collection) {
                    Collection<?> collection = (Collection<?>) first;
                    return root.get(attributeName).in(collection);
                } else {
                    return builder.like(root.get(attributeName), first.toString());
                }
            }
            case NOT_IN: {
                if (first instanceof Collection) {
                    Collection<?> collection = (Collection<?>) first;
                    return builder.not(root.get(attributeName).in(collection));
                } else {
                    return builder.notLike(root.get(attributeName), first.toString());
                }
            }
            case IS_NULL:
                return builder.isNull(root.get(attributeName));
            case NOT_NULL:
                return builder.isNotNull(root.get(attributeName));
            case BETWEEN: {
                if (first instanceof Date && second instanceof Date) {
                    return builder.between(root.get(attributeName), (Date) first, (Date) second);
                }
                if (first instanceof Number && second instanceof Number) {
                    return builder.between(root.get(attributeName), (Double) first, (Double) second);
                }
                throw new IllegalArgumentException(String.format("Object type from '%s' not supported", first.getClass().getName()));
            }
            default:
                throw new OperationNotSupportedException("Operation not supported yet");
        }
    }
}
