package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specification.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.config.DbColumns.*;

public class TagRepository extends AbstractRepository<Tag> {
    private static final String DELETE_TAG = "DELETE FROM " + tagsTable + " WHERE " + tagId + "=?;";

    public TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, tagsTable, tagId);
    }

    @Override
    public Long create(Tag tag) {
        Objects.requireNonNull(tag, "TAG CREATE: Tag is null");
        Objects.requireNonNull(tag.getName(), "TAG CREATE: Tag name is null;");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(tagName, tag.getName());
        Long id = tag.getId();
        if (id != null){
            parameters.put(tagId, id);
        }
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Integer remove(Tag tag) {
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
    public List<Tag> queryFromDatabase(Specification<Tag> specification) {
        String sql = specification.toSqlClauses();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Tag> queryFromCollection(Specification<Tag> specification) {
        throw new UnsupportedOperationException("Unsupported operation: query from collection.");
    }
}
