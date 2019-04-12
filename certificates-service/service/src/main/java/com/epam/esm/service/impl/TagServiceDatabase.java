package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor()
@Service
public class TagServiceDatabase implements TagService {
    @NonNull
    private TagRepository tagRepository;

    @Transactional
    @Override
    public TagDto create(TagDto tagDTO) {
        Tag tag = TagMapper.INSTANCE.toEntity(tagDTO);
        tagRepository.create(tag);
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<TagDto> findAll(Integer page, Integer limit) {
        Double tagCount = Double.valueOf(tagRepository.count());
        if (page > tagCount / limit){
            throw new IncorrectPaginationValues("");
        }
        return tagRepository.findAll(page, limit)
                .map(TagMapper.INSTANCE::toDto)
                .collect(toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto findByName(String name) {
        Tag tag = tagRepository.findTagByName(name).orElseThrow(() -> new EntityNotFoundException(""));
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        tagRepository.findById(id)
                .ifPresentOrElse(tag -> tagRepository.delete(tag), () -> new EntityNotFoundException(""));
    }
}
