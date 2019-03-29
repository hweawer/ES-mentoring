package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

    private final GiftCertificateService certificateService;

    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<GiftCertificateDTO> findCertificates(@RequestParam(value = "tag", required = false) String tagName,
                                                     @RequestParam(value = "column", required = false) String filterColumn,
                                                     @RequestParam(value = "value", required = false) String filterValue,
                                                     @RequestParam(value = "sort", required = false) String sortColumn){
        return certificateService.findByClause(tagName, filterColumn, filterValue, sortColumn);
    }

    @GetMapping(value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") Long id){
        return certificateService.findById(id);
    }


    @PostMapping
    public ResponseEntity<GiftCertificateDTO> create(@Valid @RequestBody GiftCertificateDTO certificateDTO,
                                                     UriComponentsBuilder builder){
        GiftCertificateDTO created = certificateService.create(certificateDTO);
        UriComponents uri = builder.path("/certificates/{ID}").buildAndExpand(created.getId());
        return ResponseEntity.created(uri.toUri()).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody GiftCertificateDTO certificateDTO, @PathVariable("id") Long id){
        certificateDTO.setId(id);
        certificateService.update(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        int rows = certificateService.delete(id);
        if (rows == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
