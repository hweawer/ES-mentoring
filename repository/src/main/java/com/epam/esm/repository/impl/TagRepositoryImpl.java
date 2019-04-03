package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.generated.Tag_;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    //todo: change localization message
    @Override
    public Tag findTagByName(String name){
        CriteriaQuery<Tag> criteriaQuery = builder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(Tag_.NAME), name));
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("tag.not.found.by.id"));
    }

    @Override
    public Tag update(Tag tag) {
        return entityManager.merge(tag);
    }
}
