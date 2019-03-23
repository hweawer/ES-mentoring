package com.epam.esm.repository.repository.specification;

/**
 * API that provides access to the search criteria
 */
public interface Specification {
    /**
     * Creates SQL query for criteria
     * @return SQL query
     */
    String toSqlClauses();
}
