package com.epam.esm.controller;

import com.epam.esm.service.SearchCertificateRequest;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.update.UpdateCertificateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

import static com.epam.esm.service.validation.ValidationScopes.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

    @NonNull
    private final GiftCertificateService certificateService;
    @NonNull
    private final UpdateCertificateService updateService;

    @GetMapping
    public List<CertificateDto> findCertificates(@Valid SearchCertificateRequest request){
        return certificateService.searchByClause(request);
    }

    @GetMapping(value = "/{id}")
    public CertificateDto findById(@Positive @PathVariable("id") Long id){
        return certificateService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> create(@Validated(onCreate.class) @RequestBody CertificateDto certificateDto){
        CertificateDto created = certificateService.create(certificateDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(onCreate.class) @RequestBody CertificateDto certificateDto,
                       @Positive @PathVariable("id") Long id){
        updateService.update(id, certificateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        certificateService.delete(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateProperty(@Validated(onPatch.class)@RequestBody CertificateDto certificateDto,
                                         @Positive @PathVariable("id") Long id){
        return updateService.update(id, certificateDto);
    }
}
