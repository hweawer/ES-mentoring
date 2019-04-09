package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/tags", produces = "application/json")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id:\\d+}")
    public TagDto findById(@PathVariable("id") Long id){
        return tagService.findById(id);
    }

    @GetMapping(value = "/{name:^[\\p{L}0-9]{3,12}}")
    public TagDto findByName(@PathVariable("name") String name){
        return tagService.findByName(name);
    }

    @GetMapping
    public Set<TagDto> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "5") Integer limit){
        return tagService.findAll(page, limit);
    }

    @PostMapping
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto tagDto, UriComponentsBuilder builder){
        TagDto created = tagService.create(tagDto);
        UriComponents uri = builder.path("/tags/{ID}").buildAndExpand(created.getId());
        return ResponseEntity.created(uri.toUri()).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

}
