package com.epam.esm.repository.specification.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LikeSpecification extends AbstractConditionSpecification {
    private static Logger logger = LogManager.getLogger();

    public LikeSpecification(String key, String regex) {
        super(key, regex);
    }

    /**
     * {@inheritDoc}
     * <code>name LIKE '%name%'</code>
     * @return SQL LIKE query based on key, value
     */
    @Override
    public String toSqlClauses() {
        String statement = key + " LIKE '" + value +"'";
        logger.debug("SQL LIKE CONDITION: " + statement);
        return statement;
    }
}
