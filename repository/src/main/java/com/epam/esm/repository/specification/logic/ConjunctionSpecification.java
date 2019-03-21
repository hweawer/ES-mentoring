package com.epam.esm.repository.specification.logic;

import com.epam.esm.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConjunctionSpecification extends AbstractLogicOperation {
    private static Logger logger = LogManager.getLogger();

    ConjunctionSpecification(Specification firstCondition, Specification secondCondition) {
        super(firstCondition, secondCondition);
    }

    /**
     * {@inheritDoc}
     * <code>name='name' AND description='description'</code>
     * @return Returns SQL AND based on conditions
     */
    @Override
    public String toSqlClauses() {
        String statement = firstCondition.toSqlClauses() + " AND " + secondCondition.toSqlClauses();
        logger.debug("SQL CONJUNCTION: " + statement);
        return statement;
    }
}
