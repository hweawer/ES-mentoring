package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.service.TagDatabaseSpecifications.*;
import static java.util.stream.Collectors.*;

@Service
public class TagServiceDatabase implements TagService {
    private static Logger logger = LogManager.getLogger();
    private Repository<Tag> tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceDatabase(Repository<Tag> tagRepository,
                              ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public TagDTO create(TagDTO tagDTO) {
        logger.debug("TAG SERVICE: create");
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        Tag createdTag = tagRepository.create(tag);
        return modelMapper.map(createdTag, TagDTO.class);
    }

    @Transactional
    @Override
    public Integer delete(Long id) {
        logger.debug("TAG SERVICE: delete");
        return tagRepository.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                    .map(tag -> modelMapper.map(tag, TagDTO.class))
                    .collect(toList());
    }

    @Transactional(readOnly = true)
    @Override
    public TagDTO findById(Long id) throws EntityNotFoundException {
        List<Tag> selected = tagRepository.findById(id);
        if (selected.isEmpty()){
            throw new EntityNotFoundException("tag.not.found.by.name");
        }
        return modelMapper.map(selected.get(0), TagDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public TagDTO findByName(String name) throws EntityNotFoundException {
        Specification specification = findTagByName(name);
        List<Tag> selected = tagRepository.queryFromDatabase(specification);
        if (selected.isEmpty()){
            throw new EntityNotFoundException("No tag with such name.");
        }
        return modelMapper.map(selected.get(0), TagDTO.class);
    }
}
