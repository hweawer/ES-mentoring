package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    @NonNull
    private final TagService tagService;

    @GetMapping(value = "/{id:\\d+}")
    public TagDto findById(@PathVariable("id") Long id){
        return tagService.findById(id);
    }

    @GetMapping(value = "/{name:^[\\p{L}0-9]{3,12}}")
    public TagDto findByName(@PathVariable("name") String name){
        return tagService.findByName(name);
    }

    @GetMapping
    public Set<TagDto> findAll(@Positive @RequestParam(required = false, defaultValue = "1") Integer page,
                               @Positive @RequestParam(required = false, defaultValue = "5") Integer limit){
        return tagService.findAll(page, limit);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto tagDto){
        TagDto created = tagService.create(tagDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        tagService.delete(id);
    }

}
