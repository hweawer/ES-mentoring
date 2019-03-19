package com.epam.esm.repository.specification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class FromSpecification<T> implements Specification<T> {
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

    @Override
    public boolean specification(T t) {
        throw new UnsupportedOperationException();
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
