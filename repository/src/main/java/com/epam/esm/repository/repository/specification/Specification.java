package com.epam.esm.repository.repository.specification;

import com.epam.esm.repository.repository.SqlQuery;

/**
 * API that provides access to the search criteria
 */
public interface Specification {
    /**
     * Creates SQL query for criteria
     * @return SQL query
     */
    SqlQuery toSqlClauses();
}
