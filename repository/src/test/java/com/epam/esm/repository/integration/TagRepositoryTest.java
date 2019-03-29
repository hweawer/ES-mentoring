package com.epam.esm.repository.integration;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.RepositoryConfig;
import com.epam.esm.repository.integration.config.RepositoryTestConfig;
import com.epam.esm.repository.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfig.class, RepositoryTestConfig.class})
@Transactional
@Sql(value = "classpath:init.sql")
@Rollback
public class TagRepositoryTest {
    @Autowired
    private Repository<Tag> tagRepository;

    @Test
    public void tagInsertTest(){
        Tag inserted = tagRepository.create(new Tag("TestTag"));
        Tag selected = tagRepository.findById(inserted.getId()).get();
        assertEquals(inserted, selected);
    }

    @Test
    public void updateTag(){
        assertThrows(UnsupportedOperationException.class, () -> {
            Tag selected = tagRepository.findById(2L).get();
            selected.setName("New name");
            tagRepository.update(selected);
        });
    }

    @Test
    public void deleteTag(){
        int rows = tagRepository.delete(5L);
        assertEquals(1, rows);
    }

    @Test
    public void deleteTagNegative(){
        int rows = tagRepository.delete(16L);
        assertEquals(0, rows);
    }

}
