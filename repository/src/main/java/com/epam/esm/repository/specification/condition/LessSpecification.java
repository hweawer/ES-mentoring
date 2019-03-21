package com.epam.esm.repository.specification.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LessSpecification extends AbstractConditionSpecification {
    private static Logger logger = LogManager.getLogger();

    public LessSpecification(String key, Object value) {
        super(key, value);
    }

    /**
     * {@inheritDoc}
     * <code>price<'5'</code>
     * @return SQL < query based on key, value
     */
    @Override
    public String toSqlClauses() {
        String statement = key + "<'" + value +"'";
        logger.debug("SQL LESS CONDITION: " + statement);
        return statement;
    }
}
