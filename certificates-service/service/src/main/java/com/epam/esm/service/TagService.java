package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.Set;

public interface TagService {
    TagDto create(TagDto t);
    Set<TagDto> findAll(Integer page, Integer limit);
    TagDto findById(Long id);
    TagDto findByName(String name);
    void delete(Long id);
}
