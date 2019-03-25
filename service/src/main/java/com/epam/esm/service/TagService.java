package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ValidationException;

import java.util.List;

public interface TagService {
    TagDTO create(TagDTO t) throws ValidationException;
    Integer delete(Long id);
    List<TagDTO> findAll();
    TagDTO findById(Long id) throws EntityNotFoundException;
    TagDTO findByName(String name) throws EntityNotFoundException;
}
