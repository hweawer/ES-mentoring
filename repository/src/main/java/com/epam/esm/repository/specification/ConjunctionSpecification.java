package com.epam.esm.repository.specification;

import com.epam.esm.repository.specification.condition.ConditionSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.stream.Collectors;

public class ConjunctionSpecification<T> implements Specification<T> {
    private static Logger logger = LogManager.getLogger();

    private Queue<ConditionSpecification<T>> specifications;

    public ConjunctionSpecification(Queue<ConditionSpecification<T>>specifications){
        this.specifications = specifications;
    }

    @Override
    public String toSqlClauses() {
        String statement = specifications.stream()
                .map(Specification::toSqlClauses)
                .collect(Collectors.joining(" AND "));
        logger.debug("SQL CONJUNCTION: " + statement);
        return statement;
    }

    @Override
    public boolean specification(T t) {
        throw new UnsupportedOperationException();
    }
}
