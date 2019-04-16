package com.epam.esm.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.tag.TagSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {
    private TagSearchService findTagService;

    @GetMapping("/tags/top")
    public TagDto findMostPopularTag(){
        return findTagService.mostPopularUserTag();
    }
}
