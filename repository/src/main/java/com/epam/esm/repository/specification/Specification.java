package com.epam.esm.repository.specification;

import java.util.function.Predicate;

public interface Specification<T> {
    String toSqlClauses();
    boolean specification(T t);
    default Predicate<T> toPredicate(){
        return this::specification;
    }
}
