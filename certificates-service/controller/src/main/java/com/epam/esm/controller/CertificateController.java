package com.epam.esm.controller;

import com.epam.esm.entity.Role;
import com.epam.esm.service.find.FindCertificateService;
import com.epam.esm.service.find.SearchCertificateRequest;
import com.epam.esm.service.create.CreateCertificateService;
import com.epam.esm.service.delete.DeleteCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.update.UpdateCertificateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
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
import java.util.List;
import java.util.Locale;

import static com.epam.esm.service.validation.ValidationScopes.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    @NonNull
    private final FindCertificateService searchService;
    @NonNull
    private final UpdateCertificateService updateService;
    @NonNull
    private final CreateCertificateService createService;
    @NonNull
    private final DeleteCertificateService deleteService;
    @NonNull
    private final MessageSource messageSource;

    @GetMapping
    public List<CertificateDto> findCertificates(@Valid SearchCertificateRequest request){
        return searchService.searchByClause(request);
    }

    @GetMapping(value = "/{id}")
    public CertificateDto findById(@Positive @PathVariable("id") Long id){
        return searchService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CertificateDto> create(@Validated(onCreate.class) @RequestBody CertificateDto certificateDto){
        CertificateDto created = createService.createCertificate(certificateDto);
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
        updateService.putUpdate(id, certificateDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteService.deleteCertificate(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateProperty(@Validated(onPatch.class)@RequestBody CertificateDto certificateDto,
                                         @Positive @PathVariable("id") Long id){
        return updateService.patchUpdate(id, certificateDto);
    }

}
