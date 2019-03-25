package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.service.DatabaseSpecifications;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceDatabase implements TagService {
    private static Logger logger = LogManager.getLogger();
    private static Validator validator;
    private Repository<Tag> tagRepository;
    private DatabaseSpecifications specificationCreator;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceDatabase(Repository<Tag> tagRepository,
                              DatabaseSpecifications specificationCreator,
                              ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.specificationCreator = specificationCreator;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
        this.modelMapper = modelMapper;
    }

    @Override
    public TagDTO create(TagDTO tagDTO) throws ServiceException {
        logger.debug("TAG SERVICE: create");
        validateDTO(tagDTO);
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        Tag createdTag = tagRepository.create(tag);
        return modelMapper.map(createdTag, TagDTO.class);
    }

    @Override
    public Integer remove(Long id) {
        logger.debug("TAG SERVICE: remove");
        return tagRepository.remove(id);
    }

    @Override
    public List<TagDTO> findAll() {
        Specification specification = DatabaseSpecifications.findAllTags();
        List<Tag> tags = tagRepository.queryFromDatabase(specification);
        List<TagDTO> tagDTOs = tags.stream()
                                    .map(tag -> modelMapper.map(tag, TagDTO.class))
                                    .collect(Collectors.toList());
        return tagDTOs;
    }

    @Override
    public TagDTO findById(Long id) throws ServiceException {
        Specification specification = DatabaseSpecifications.findTagById();
        Object[] params = new Object[]{id};
        List<Tag> selected = tagRepository.queryFromDatabase(specification, params);
        if (selected.isEmpty()){
            throw new ServiceException("No tag with such id.");
        }
        return modelMapper.map(selected.get(0), TagDTO.class);
    }

    @Override
    public TagDTO findByName(String name) throws ServiceException {
        Specification specification = DatabaseSpecifications.findTagByName();
        Object[] params = new Object[]{name};
        List<Tag> selected = tagRepository.queryFromDatabase(specification, params);
        if (selected.isEmpty()){
            throw new ServiceException("No tag with such name.");
        }
        return modelMapper.map(selected.get(0), TagDTO.class);
    }

    private void validateDTO(TagDTO tagDTO) throws ServiceException {
        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);
        if(!violations.isEmpty()){
            Set<String> violationMessages = new HashSet<>();
            violations.forEach(violation -> violationMessages.add(violation.getPropertyPath()
                    + " : " + violation.getMessage()));
            throw new ServiceException("Tag isn't valid:\n" + StringUtils.join(violationMessages, '\n'));
        }
    }
}
