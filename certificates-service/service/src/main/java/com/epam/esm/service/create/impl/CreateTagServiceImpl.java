package com.epam.esm.service.create.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.create.CreateTagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.InstanceExistsException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateTagServiceImpl implements CreateTagService {
    @NonNull
    private TagRepository tagRepository;

    @Transactional
    @Override
    public TagDto createTag(TagDto dto) {
        Tag tag = TagMapper.INSTANCE.toEntity(dto);
        Optional<Tag> select = tagRepository.findTagByName(tag.getName());
        if (select.isPresent()) throw new InstanceExistsException("tag.name.exists");
        tagRepository.create(tag);
        return TagMapper.INSTANCE.toDto(tag);
    }
}
