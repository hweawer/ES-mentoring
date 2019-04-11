package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        final String TAG_BY_NAME = "select t from Tag t where t.name=:name";
        return entityManager.createQuery(TAG_BY_NAME, Tag.class).setParameter("name", name).getResultStream()
                .findFirst();
    }
}
