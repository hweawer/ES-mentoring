package com.epam.esm.repository.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.epam.esm.repository.config.TagTable.*;

@Repository
public class TagRepository extends AbstractRepository<Tag> {
    private static final Logger logger = LogManager.getLogger();

    private final BeanPropertyRowMapper<Tag> tagMapper;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate, BeanPropertyRowMapper<Tag> tagMapper) {
        super(jdbcTemplate, tableName, id);
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        Objects.requireNonNull(tag, "TAG CREATE: Tag is null");
        Objects.requireNonNull(tag.getName(), "TAG CREATE: Tag name is null;");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(name, tag.getName());
        Long insertedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        tag.setId(insertedId);
        logger.debug("Tag entity: " + tag + " was created.");
        return tag;
    }

    @Override
    public Integer remove(Tag tag) {
        Objects.requireNonNull(tag, "TAG REMOVE: Tag is null");
        String DELETE_TAG = "DELETE FROM " + tableName + " WHERE " + tagId + "=?;";
        Integer affectedRows = remove(DELETE_TAG, tag.getId());
        logger.debug("Tag entity: " + tag + " was deleted.");
        return affectedRows;
    }

    @Override
    public Integer update(Tag tag) {
        throw new UnsupportedOperationException("Tags can't be updated");
    }

    @Override
    public List<Tag> queryFromDatabase(Specification specification) {
        String sql = specification.toSqlClauses();
        return jdbcTemplate.query(sql, tagMapper);
    }

    @Override
    public List<Tag> queryFromDatabase(Specification specification, Object[] args) {
        String sql = specification.toSqlClauses();
        return jdbcTemplate.query(sql, args, tagMapper);
    }
}
