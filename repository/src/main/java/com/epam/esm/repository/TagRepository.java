package com.epam.esm.repository;

import com.epam.esm.config.DbColumns;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.config.DbColumns.*;

@Repository
public class TagRepository extends AbstractRepository<Tag> {
    private static final Logger logger = LogManager.getLogger();

    public TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, tagsTable, id);
    }

    @Override
    public Tag create(Tag tag) {
        Objects.requireNonNull(tag, "TAG CREATE: Tag is null");
        Objects.requireNonNull(tag.getName(), "TAG CREATE: Tag name is null;");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(name, tag.getName());
        Long id = tag.getId();
        if (id != null){
            parameters.put(DbColumns.id, id);
        }
        Long insertedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        tag.setId(insertedId);
        return tag;
    }

    @Override
    public Integer remove(Tag tag) {
        String DELETE_TAG = "DELETE FROM " + tagsTable + " WHERE " + tagId + "=?;";
        Objects.requireNonNull(tag, "TAG REMOVE: Tag is null");
        Long id = tag.getId();
        Objects.requireNonNull(id, "TAG REMOVE: Tag id is null");
        return remove(DELETE_TAG, id);
    }

    @Override
    public Integer update(Tag tag) {
        throw new UnsupportedOperationException("Tags can't be updated");
    }

    @Override
    public List<Tag> queryFromDatabase(Specification specification) {
        String sql = specification.toSqlClauses();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Tag> queryFromDatabase(Collection<Specification> specification) {
        String sql = specification.stream()
                .map(Specification::toSqlClauses)
                .collect(Collectors.joining(" ")) + ";";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }
}
