package com.epam.esm.repository.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Types;

public abstract class AbstractRepository<T> implements Repository<T> {
    @Autowired
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

    protected int remove(String sql, Long id){
        Object[] param = {id};
        int[] types = {Types.BIGINT};
        return jdbcTemplate.update(sql, param, types);
    }
}
