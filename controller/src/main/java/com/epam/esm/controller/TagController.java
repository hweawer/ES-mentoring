package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private static Logger logger = LogManager.getLogger();
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> findAll(){
        logger.debug("findAll tags");
        return tagService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TagDTO findById(@PathVariable("id") Long id){
        try {
            logger.debug("findById tag");
            return tagService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find tag", e);
        }
    }

    @GetMapping(value = "/name/{name}")
    public TagDTO findByName(@PathVariable("name") String name){
        try {
            logger.debug("findByName tag");
            return tagService.findByName(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find tag", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO){
        logger.debug("create tag");
        return tagService.create(tagDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

}
