package com.epam.esm.service.tag.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.InstanceExistsException;
import com.epam.esm.service.tag.CreateTagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CreateTagServiceImpl implements CreateTagService {
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public TagDto createTag(TagDto dto) {
        Tag tag = TagMapper.INSTANCE.toEntity(dto);
        tagRepository.findTagByName(tag.getName()).ifPresentOrElse(o -> {
            throw new InstanceExistsException();
        }, () -> tagRepository.create(tag));
        return TagMapper.INSTANCE.toDto(tag);
    }
}
