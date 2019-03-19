package com.epam.esm.repository.specification;

import com.epam.esm.repository.specification.condition.ConditionSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class WhereSpecification<T> implements Specification<T> {
    private static Logger logger = LogManager.getLogger();

    private Queue<ConditionSpecification<T>> conditions;

    public WhereSpecification() { conditions = new LinkedList<>(); }

    public WhereSpecification(Queue<ConditionSpecification<T>> conditions){
        this.conditions = conditions;
    }

    @Override
    public String toSqlClauses() {
        StringBuilder statement = new StringBuilder("WHERE ");
        ConjunctionSpecification<T> conjunction = new ConjunctionSpecification<>(conditions);
        String conjunct = conjunction.toSqlClauses();
        statement.append(conjunct);
        logger.debug("SQL WHERE : " + statement);
        return statement.toString();
    }

    @Override
    public boolean specification(T t) {
        throw new UnsupportedOperationException();
    }

    public boolean addSpecification(ConditionSpecification<T> condition){
        return conditions.add(condition);
    }
}
