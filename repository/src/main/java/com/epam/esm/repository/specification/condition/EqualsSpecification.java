package com.epam.esm.repository.specification.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EqualsSpecification extends AbstractConditionSpecification {
    private static Logger logger = LogManager.getLogger();

    public EqualsSpecification(String key, Object value) {
        super(key, value);
    }

    /**
     * {@inheritDoc}
     * <code>name='name'</code>
     * @return Returns SQL = query based on key, value
     */
    @Override
    public String toSqlClauses() {
        String statement = key + "='" + value +"'";
        logger.debug("SQL EQUALS CONDITION: " + statement);
        return statement;
    }
}
