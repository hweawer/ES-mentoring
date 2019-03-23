package com.epam.esm.repository.repository.specification;

public class SpecificationBuilder implements Specification {
    private final StringBuilder sql;

    public SpecificationBuilder(){
        sql = new StringBuilder();
    }

    @Override
    public String toSqlClauses() {
        return sql.toString();
    }

    public SpecificationBuilder select() {
        sql.append("SELECT *");
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

    public SpecificationBuilder equal(String column) {
        sql.append(column).append("=?");
        return this;
    }

    public SpecificationBuilder more(String column) {
        sql.append(column).append(">?");
        return this;
    }

    public SpecificationBuilder less(String column) {
        sql.append(column).append("<?");
        return this;
    }

    public SpecificationBuilder like(String column) {
        sql.append(column).append(" LIKE ?");
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
