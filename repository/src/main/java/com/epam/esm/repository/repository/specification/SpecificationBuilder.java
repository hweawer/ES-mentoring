package com.epam.esm.repository.repository.specification;

import com.epam.esm.repository.repository.SqlQuery;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder implements Specification {
    private final StringBuilder sql;
    private List<Object> params = new ArrayList<>();

    public SpecificationBuilder(){
        sql = new StringBuilder();
    }

    @Override
    public SqlQuery toSqlClauses() {
        return new SqlQuery(sql.toString(), params.toArray(Object[]::new));
    }

    public SpecificationBuilder select() {
        sql.append("SELECT *");
        return this;
    }

    public SpecificationBuilder select(String... columns) {
        sql.append("SELECT ");
        String columnsArg = String.join(", ", columns);
        sql.append(columnsArg);
        return this;
    }

    public SpecificationBuilder from(String tableName) {
        sql.append(" FROM ").append(tableName);
        return this;
    }

    public SpecificationBuilder where() {
        sql.append(" WHERE ");
        return this;
    }

    public SpecificationBuilder and() {
        sql.append(" AND ");
        return this;
    }

    public SpecificationBuilder or() {
        sql.append(" OR ");
        return this;
    }

    public SpecificationBuilder groupBy(String column) {
        sql.append(" GROUP BY ").append(column);
        return this;
    }

    public SpecificationBuilder having() {
        sql.append(" HAVING ");
        return this;
    }

    public SpecificationBuilder equivalent(String column, Object arg) {
        sql.append(column).append("=?");
        params.add(arg);
        return this;
    }

    public SpecificationBuilder more(String column, Number arg) {
        sql.append(column).append(">?");
        params.add(arg);
        return this;
    }

    public SpecificationBuilder less(String column, Number arg) {
        sql.append(column).append("<?");
        params.add(arg);
        return this;
    }

    public SpecificationBuilder like(String column, String arg) {
        sql.append(column).append(" LIKE ?");
        params.add(arg);
        return this;
    }

    public SpecificationBuilder orderBy(String column, boolean desc) {
        sql.append(" ORDER BY ").append(column);
        if(desc){
            sql.append(" DESC ");
        }
        return this;
    }

    public SpecificationBuilder orderBy(String column) {
        sql.append(" ORDER BY ").append(column);
        return this;
    }

    public SpecificationBuilder innerJoin(String joinTable, String fromColumn, String joinColumn) {
        sql.append(" INNER JOIN ")
                .append(joinTable)
                .append(" ON ")
                .append(fromColumn).append(" = ").append(joinColumn);
        return this;
    }

}
