package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tags", consumes = "application/json")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> findAll(){
        return tagService.findAll();
    }

    @GetMapping(value = "/{id:[0-9]+}")
    public TagDTO findById(@PathVariable("id") Long id){
        return tagService.findById(id);
    }

    @GetMapping(value = "/{name:[a-zA-Z]+}")
    public TagDTO findByName(@PathVariable("name") String name){
        return tagService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO){
        return tagService.create(tagDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

}
