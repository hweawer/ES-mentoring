package com.epam.esm.repository.specification.join;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InnerJoin<T> extends JoinSpecification<T> {
    private static Logger logger = LogManager.getLogger();

    public InnerJoin(String joinTable, String fromColumn, String joinColumn) {
        super(joinTable, fromColumn, joinColumn);
    }

    @Override
    public String toSqlClauses() {
        String statement = "INNER JOIN " + joinTable + " ON " + fromColumn + "=" + joinColumn;
        logger.debug("SQL INNER JOIN: " + statement);
        return statement;
    }
}
