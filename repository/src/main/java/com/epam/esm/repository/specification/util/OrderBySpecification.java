package com.epam.esm.repository.specification.util;

import com.epam.esm.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderBySpecification implements Specification {
    private static Logger logger = LogManager.getLogger();
    private String column;
    private boolean asc = true;

    public OrderBySpecification(String column){
        this.column = column;
    }

    /**
     * {@inheritDoc}''
     * <code>ORDER BY name</code>
     * @return Returns SQL ORDER BY based on column
     */
    @Override
    public String toSqlClauses() {
        String statement = "ORDER BY " + column;
        if (!asc) statement += " DESC";
        logger.debug("SQL ORDER BY: " + statement);
        return statement;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
