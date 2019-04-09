package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

    private final GiftCertificateService certificateService;

    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<CertificateDto> findCertificates(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit,
                                                 @RequestParam(value = "tag", required = false) String tagName,
                                                 @RequestParam(value = "column", required = false) String filterColumn,
                                                 @RequestParam(value = "value", required = false) String filterValue,
                                                 @RequestParam(value = "sort", required = false) String sortColumn){
        return certificateService.findByClause(page, limit, tagName, filterColumn, filterValue, sortColumn);
    }

    @GetMapping(value = "/{id}")
    public CertificateDto findById(@PathVariable("id") Long id){
        return certificateService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CertificateDto> create(@Valid @RequestBody CertificateDto certificateDTO,
                                                 UriComponentsBuilder builder){
        CertificateDto created = certificateService.create(certificateDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody CertificateDto certificateDTO, @PathVariable("id") Long id){
        certificateDTO.setId(id);
        certificateService.update(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        certificateService.delete(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateProperty(@Valid @RequestBody CertificateDto certificateDto, @PathVariable("id") Long id){
        return certificateService.updateCost(id, certificateDto.getPrice());
    }
}