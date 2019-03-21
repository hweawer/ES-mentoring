package com.epam.esm.repository.specification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class FromSpecification implements Specification {
    private static Logger logger = LogManager.getLogger();

    private String table;
    private Queue<String> columns;

    public FromSpecification(String table){
        this.table = table;
    }

    public FromSpecification(String... columns){
        this.columns = new LinkedList<>();
        for (String column: columns) {
            this.columns.offer(column);
        }
    }

    public FromSpecification(String table, String... columns){
        this.table = table;
        this.columns = new LinkedList<>();
        for (String column: columns) {
            this.columns.offer(column);
        }
    }

    /**
     * {@inheritDoc}
     * <code>SELECT * FROM certificates</code>
     * @return SQL FROM query based on table
     */
    @Override
    public String toSqlClauses() {
        StringBuilder statement = new StringBuilder("SELECT ");
        if (this.columns == null || this.columns.isEmpty()){
            statement.append("* ");
        }
        else {
            String columns = String.join(", ", this.columns);
            statement.append(columns).append(" ");
        }
        statement.append("FROM ").append(Objects.requireNonNull(table));
        logger.debug("SQL FROM: " + statement);
        return statement.toString();
    }

    public void setColumns(Queue<String> columns) {
        this.columns = columns;
    }

    public Queue<String> getColumns() {
        return columns;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}
