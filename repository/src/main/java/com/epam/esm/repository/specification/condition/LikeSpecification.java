package com.epam.esm.repository.specification.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LikeSpecification<T> extends ConditionSpecification<T> {
    private static Logger logger = LogManager.getLogger();

    public LikeSpecification(String key, Object value) {
        super(key, value);
    }

    @Override
    public String toSqlClauses() {
        String statement = key + " LIKE '" + value +"'";
        logger.debug("SQL LIKE : " + statement);
        return statement;
    }
}
