package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private static Logger logger = LogManager.getLogger();

    private final GiftCertificateService certificateService;

    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<GiftCertificateDTO> findAll(){
        return certificateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") Long id){
        try {
            logger.debug("findById certificate");
            return certificateService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find tag", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO create(@Valid @RequestBody GiftCertificateDTO certificateDTO){
        logger.debug("create tag");
        return certificateService.create(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        certificateService.delete(id);
    }

}
