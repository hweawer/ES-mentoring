package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates", consumes = "application/json")
public class CertificateController {

    private final GiftCertificateService certificateService;

    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    //todo: implement method
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


    //todo: check if header is present & status
    @PostMapping
    public ResponseEntity<GiftCertificateDTO> create(@Valid @RequestBody GiftCertificateDTO certificateDTO,
                                                     UriComponentsBuilder builder){
        GiftCertificateDTO created = certificateService.create(certificateDTO);
        UriComponents uri = builder.path("/certificates/{id}").buildAndExpand(created.getId());
        return ResponseEntity.created(uri.toUri()).body(created);
    }

    //todo: think about return in body
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody GiftCertificateDTO certificateDTO, @PathVariable("id") Long id){
        certificateDTO.setId(id);
        certificateService.update(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        certificateService.delete(id);
    }
}
