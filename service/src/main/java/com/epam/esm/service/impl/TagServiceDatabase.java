package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
public class TagServiceDatabase implements TagService {
    private TagRepository tagRepository;

    private static final Integer MAX_PAGE = 50;
    private static final Integer MIN_PAGE = 1;
    private static final Integer MAX_LIMIT = 20;
    private static final Integer MIN_LIMIT = 5;

    public TagServiceDatabase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public TagDto create(TagDto tagDTO) {
        Tag tag = TagMapper.INSTANCE.tagDtoToTag(tagDTO);
        tagRepository.create(tag);
        return TagMapper.INSTANCE.tagToTagDto(tag);
    }

    //todo: localization message
    @Transactional(readOnly = true)
    @Override
    public Set<TagDto> findAll(Integer page, Integer limit) {
        if (page > MAX_PAGE || page < MIN_PAGE || limit < MIN_LIMIT || limit > MAX_LIMIT){
            throw new IncorrectPaginationValues("");
        }
        return tagRepository.findAll(page, limit)
                .map(TagMapper.INSTANCE::tagToTagDto)
                .collect(toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        return TagMapper.INSTANCE.tagToTagDto(tag);
    }

    //todo: localization message
    @Transactional(readOnly = true)
    @Override
    public TagDto findByName(String name) {
        Tag tag = tagRepository.findTagByName(name).orElseThrow(() -> new EntityNotFoundException(""));
        return TagMapper.INSTANCE.tagToTagDto(tag);
    }

    //todo: localization message
    @Transactional
    @Override
    public void delete(Long id) {
        tagRepository.findById(id)
                .ifPresentOrElse(tag -> tagRepository.delete(tag), () -> new EntityNotFoundException(""));
    }
}
