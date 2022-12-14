package com.justedlev.auth.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Criteria {

    private String attributeName;
    private QueryOperator operator;
    private Object first;
    private Object second;

    public Criteria(String attributeName, QueryOperator operator, Object value) {
        this(attributeName, operator);
        this.first = value;
    }

    public Criteria(String attributeName, QueryOperator operator) {
        this.attributeName = attributeName;
        this.operator = operator;
    }

}
