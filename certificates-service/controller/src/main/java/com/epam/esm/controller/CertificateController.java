package com.epam.esm.controller;

import com.epam.esm.service.certificate.CertificateSearchService;
import com.epam.esm.service.certificate.impl.SearchCertificateRequest;
import com.epam.esm.service.certificate.CreateCertificateService;
import com.epam.esm.service.certificate.DeleteCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.certificate.UpdateCertificateService;
import com.epam.esm.service.dto.Links;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import com.epam.esm.service.dto.mapper.CertificatePartionalUpdateMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.stream.Collectors;

import static com.epam.esm.service.validation.ValidationScopes.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    private final CertificateSearchService searchCertificatesService;
    private final UpdateCertificateService updateCertificatesService;
    private final CreateCertificateService createCertificatesService;
    private final DeleteCertificateService deleteCertificatesService;

    @GetMapping
    public PaginationDto<CertificateDto> findCertificates(@Valid SearchCertificateRequest request){
        PaginationInfoDto<CertificateDto> paginationInfoDto = searchCertificatesService.searchByClause(request);
        Integer pageCount = paginationInfoDto.getPageInfo().getPageCount();
        String path = "/certificates?";
        if (!request.getTag().isEmpty() || request.getColumn() != null && request.getSort() != null) {
            if (request.getColumn() != null) {
                path += "column=" + request.getValue() + "&";
            }
            if (request.getSort() != null) {
                path += "sort=" + request.getSort() + "&";
            }
            if (!request.getTag().isEmpty()) {
                String tags = request.getTag().stream()
                        .map(name -> "tag=" + name)
                        .collect(Collectors.joining("&", "", "&"));
                path += tags;
            }
        }
        Integer limit = request.getLimit();
        Integer page = request.getPage();
        String previous = page == 1 ? null : "/certificates?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount == 0 ? 1 : pageCount) ? null : "/certificates?page=" + (page + 1) + "&limit=" + limit;
        Links links = new Links(path + "page=1&limit=" + limit,
                path + "page=" + (pageCount == 0 ? 1 : pageCount) + "&limit=" + limit,
                next, previous);
        PaginationDto<CertificateDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(paginationInfoDto.getCollection());
        paginationDto.setLinks(links);
        return paginationDto;
    }

    @GetMapping(value = "/{id}")
    public CertificateDto findById(@Positive @PathVariable("id") Long id){
        return searchCertificatesService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> create(@Validated(onCreate.class) @RequestBody CertificateDto certificateDto){
        CertificateDto created = createCertificatesService.createCertificate(certificateDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(onCreate.class) @RequestBody CertificateDto certificateDto,
                       @Positive @PathVariable("id") Long id){
        updateCertificatesService.update(id, certificateDto, CertificateFullUpdateMapper.INSTANCE);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteCertificatesService.deleteCertificate(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateProperty(@Validated(onPatch.class)@RequestBody CertificateDto certificateDto,
                                         @Positive @PathVariable("id") Long id){
        return updateCertificatesService.update(id, certificateDto, CertificatePartionalUpdateMapper.INSTANCE);
    }

}
