package com.epam.esm.repository.specification.join;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InnerJoin extends AbstractJoinSpecification {
    private static Logger logger = LogManager.getLogger();

    public InnerJoin(String joinTable, String fromColumn, String joinColumn) {
        super(joinTable, fromColumn, joinColumn);
    }

    /**
     * {@inheritDoc}
     * <code>INNER JOIN certificates_tags ON id=certificates_id</code>
     * @return Returns SQL INNER JOIN based on joinTable, fromColumn, joinColumn
     */
    @Override
    public String toSqlClauses() {
        String statement = "INNER JOIN " + joinTable + " ON " + fromColumn + "=" + joinColumn;
        logger.debug("SQL INNER JOIN: " + statement);
        return statement;
    }
}
