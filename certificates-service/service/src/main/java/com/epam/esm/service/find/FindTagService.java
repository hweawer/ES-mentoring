package com.epam.esm.service.find;

import com.epam.esm.service.dto.TagDto;

import java.util.Set;

public interface FindTagService {
    Set<TagDto> findAll(Integer page, Integer limit);

    TagDto findById(Long id);

    TagDto findByName(String name);
}
