package com.epam.esm.controller;

import com.epam.esm.service.dto.Links;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.tag.CreateTagService;
import com.epam.esm.service.tag.DeleteTagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.tag.TagSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagSearchService searchTagService;
    private final CreateTagService createTagService;
    private final DeleteTagService deleteTagService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{id:\\d+}")
    public TagDto findById(@PathVariable("id") Long id){
        return searchTagService.findById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{name:^[\\p{L}0-9]{3,12}}")
    public TagDto findByName(@PathVariable("name") String name){
        return searchTagService.findByName(name);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public PaginationDto<TagDto> findAll(@Positive @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @Positive @RequestParam(required = false, defaultValue = "5") Integer limit){
        PaginationInfoDto<TagDto> paginationInfoDto = searchTagService.findAll(page, limit);
        Integer pageCount = paginationInfoDto.getPageInfo().getPageCount();
        String previous = page == 1 ? null : "/tags?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount == 0 ? 1 : pageCount) ? null : "/tags?page=" + (page + 1) + "&limit=" + limit;
        PaginationDto<TagDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(paginationInfoDto.getCollection());

        Links links = new Links("/tags?page=1&limit=" + limit,
                "/tags?page=" + (pageCount == 0 ? 1 : pageCount) + "&limit=" + limit,
                next, previous);

        paginationDto.setLinks(links);
        return paginationDto;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto tagDto){
        TagDto created = createTagService.createTag(tagDto);
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
        deleteTagService.deleteTag(id);
    }

}
