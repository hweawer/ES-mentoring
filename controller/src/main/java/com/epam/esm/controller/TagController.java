package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tags", consumes = "application/json", produces = "application/json")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> findAll(){
        return tagService.findAll();
    }

    @GetMapping(value = "/{id:\\d+}")
    public TagDTO findById(@PathVariable("id") Long id){
        return tagService.findById(id);
    }

    @GetMapping(value = "/{name:^[\\p{L}0-9]{3,12}}")
    public TagDTO findByName(@PathVariable("name") String name){
        return tagService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody TagDTO tagDTO, UriComponentsBuilder builder){
        TagDTO created = tagService.create(tagDTO);
        UriComponents uri = builder.path("/tags/{id}").buildAndExpand(created.getId());
        return ResponseEntity.created(uri.toUri()).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

}
