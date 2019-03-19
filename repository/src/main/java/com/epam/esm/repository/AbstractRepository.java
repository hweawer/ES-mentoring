package com.epam.esm.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Types;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected String rootTable;
    protected String generatedIdColumn;
    protected JdbcTemplate jdbcTemplate;
    protected SimpleJdbcInsert simpleJdbcInsert;

    AbstractRepository(JdbcTemplate jdbcTemplate, String rootTable, String generatedIdColumn){
        this.jdbcTemplate = jdbcTemplate;
        this.rootTable = rootTable;
        this.generatedIdColumn = generatedIdColumn;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(rootTable)
                .usingGeneratedKeyColumns(generatedIdColumn);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected int remove(String sql, Long id){
        Object[] param = {id};
        int[] types = {Types.BIGINT};
        return jdbcTemplate.update(sql, param, types);
    }
}
