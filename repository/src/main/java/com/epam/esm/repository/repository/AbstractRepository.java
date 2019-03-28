package com.epam.esm.repository.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public abstract class AbstractRepository<T> implements Repository<T> {
    protected JdbcTemplate jdbcTemplate;
    protected SimpleJdbcInsert simpleJdbcInsert;

    AbstractRepository(JdbcTemplate jdbcTemplate, String rootTable, String generatedIdColumn, String schema){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(rootTable)
                .usingGeneratedKeyColumns(generatedIdColumn)
                .withSchemaName(schema);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected int delete(String sql, Long id){
        return jdbcTemplate.update(sql, id);
    }
}
