package com.epam.esm.service.application.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.Repository;
import com.epam.esm.service.application.DatabaseSpecificationCreator;
import com.epam.esm.service.application.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceDatabase implements TagService {
    private Repository<Tag> tagRepository;
    private DatabaseSpecificationCreator specificationCreator;

    @Autowired
    public TagServiceDatabase(Repository<Tag> tagRepository, DatabaseSpecificationCreator specificationCreator) {
        this.tagRepository = tagRepository;
        this.specificationCreator = specificationCreator;
    }

    @Override
    public Long create(Tag tag) {
        return tagRepository.create(tag).getId();
    }

    @Override
    public Integer remove(Tag tag) {
        return tagRepository.remove(tag);
    }

    @Override
    public Integer update(Tag tag) {
        throw new UnsupportedOperationException("Tags can't be updated");
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.queryFromDatabase(specificationCreator.findAllTags());
    }

    @Override
    public Tag findById(Long id) {
        List<Tag> tag = tagRepository.queryFromDatabase(specificationCreator.findById(id, Tag.class));
        return tag.isEmpty() ? null : tag.get(0);
    }

    @Override
    public Tag tagByName(String name) {
        List<Tag> tag = tagRepository.queryFromDatabase(specificationCreator.tagByName(name));
        return tag.isEmpty() ? null : tag.get(0);
    }
}
