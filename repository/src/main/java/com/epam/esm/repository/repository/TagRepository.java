package com.epam.esm.repository.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.RepositoryConfig;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TagRepository extends AbstractRepository<Tag> {
    private static final Logger logger = LogManager.getLogger();

    private final BeanPropertyRowMapper<Tag> tagMapper;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate, BeanPropertyRowMapper<Tag> tagMapper) {
        super(jdbcTemplate, TagTable.tableName, TagTable.id, RepositoryConfig.schemaInUse);
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        Objects.requireNonNull(tag, "TAG CREATE: Tag is null");
        final String INSERT_TAG = "INSERT INTO " + TagTable.tableName + "(" + TagTable.name + ")"
                + " VALUES(?) ON CONFLICT (" + TagTable.name + ") DO UPDATE SET id = excluded.id returning "
                + TagTable.id;
        Long insertedId = jdbcTemplate.queryForObject(INSERT_TAG, new Object[]{tag.getName()}, Long.class);
        tag.setId(insertedId);
        logger.debug("Tag entity: " + tag + " was created.");
        return tag;
    }

    @Override
    public Integer delete(Long id) {
        final String DELETE_TAG = "DELETE FROM " + TagTable.tableName + " WHERE " + TagTable.id + "=?;";
        return delete(DELETE_TAG, id);
    }

    @Override
    public Integer update(Tag tag) {
        throw new UnsupportedOperationException("Tags can't be updated");
    }

    @Override
    public List<Tag> queryFromDatabase(Specification specification) {
        SqlQuery sqlQuery = specification.toSqlClauses();
        return jdbcTemplate.query(sqlQuery.getSql(), sqlQuery.getParams(), tagMapper);
    }

    @Override
    public List<Tag> findAll() {
        final String SELECT_ALL = "SELECT * FROM " + TagTable.tableName;
        return jdbcTemplate.query(SELECT_ALL, tagMapper);
    }

    @Override
    public List<Tag> findById(Long id) {
        final String SELECT_BY_ID = "SELECT * FROM " + TagTable.tableName + " WHERE " + TagTable.id + "=?";
        return jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, tagMapper);
    }
}
