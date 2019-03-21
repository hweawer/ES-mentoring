package com.epam.esm.repository.specification.join;

import com.epam.esm.repository.specification.Specification;

public abstract class AbstractJoinSpecification implements Specification {
    protected String joinTable;
    protected String fromColumn;
    protected String joinColumn;

    public AbstractJoinSpecification(String joinTable, String fromColumn, String joinColumn){
        this.joinTable = joinTable;
        this.fromColumn = fromColumn;
        this.joinColumn = joinColumn;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(String fromColumn) {
        this.fromColumn = fromColumn;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public void setJoinColumn(String joinColumn) {
        this.joinColumn = joinColumn;
    }
}
