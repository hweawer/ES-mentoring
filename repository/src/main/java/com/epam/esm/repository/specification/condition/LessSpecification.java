package com.epam.esm.repository.specification.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LessSpecification<T> extends ConditionSpecification<T> {
    private static Logger logger = LogManager.getLogger();

    public LessSpecification(String key, Object value) {
        super(key, value);
    }

    @Override
    public String toSqlClauses() {
        String statement = key + "<'" + value +"'";
        logger.debug("SQL EQUALS CONDITION: " + statement);
        return statement;
    }
}
