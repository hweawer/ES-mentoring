package com.epam.esm.controller;

import com.epam.esm.entity.Role;
import com.epam.esm.service.create.CreateTagService;
import com.epam.esm.service.delete.DeleteTagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.find.FindTagService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    @NonNull
    private final FindTagService searchService;
    @NonNull
    private final CreateTagService createService;
    @NonNull
    private final DeleteTagService deleteService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{id:\\d+}")
    public TagDto findById(@PathVariable("id") Long id){
        return searchService.findById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{name:^[\\p{L}0-9]{3,12}}")
    public TagDto findByName(@PathVariable("name") String name){
        return searchService.findByName(name);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public Set<TagDto> findAll(@Positive @RequestParam(required = false, defaultValue = "1") Integer page,
                               @Positive @RequestParam(required = false, defaultValue = "5") Integer limit){
        return searchService.findAll(page, limit);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto tagDto){
        TagDto created = createService.createTag(tagDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteService.deleteTag(id);
    }

}
