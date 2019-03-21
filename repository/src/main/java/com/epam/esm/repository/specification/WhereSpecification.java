package com.epam.esm.repository.specification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhereSpecification implements Specification {
    private static Logger logger = LogManager.getLogger();

    private Specification condition;

    public WhereSpecification() {}

    public WhereSpecification(Specification condition){
        this.condition = condition;
    }

    /**
     * {@inheritDoc}
     * <code> WHERE name='name'</code>
     * @return Returns SQL WHERE query based on condition
     */
    @Override
    public String toSqlClauses() {
        StringBuilder statement = new StringBuilder("WHERE ");
        statement.append(condition.toSqlClauses());
        logger.debug("SQL WHERE : " + statement);
        return statement.toString();
    }

}
