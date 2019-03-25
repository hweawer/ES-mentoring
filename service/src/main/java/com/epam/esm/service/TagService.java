package com.epam.esm.service;

import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface TagService {
    TagDTO create(TagDTO t) throws ServiceException;
    Integer remove(Long id);
    List<TagDTO> findAll();
    TagDTO findById(Long id) throws ServiceException;
    TagDTO findByName(String name) throws ServiceException;
}
