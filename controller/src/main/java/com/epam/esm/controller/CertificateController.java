package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.util.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates", consumes = "application/json")
public class CertificateController {

    private final GiftCertificateService certificateService;

    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<GiftCertificateDTO> findCertificates(){
        return certificateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") Long id){
        return certificateService.findById(id);
    }

    @GetMapping("/query")
    public List<GiftCertificateDTO> findSortedByName(@RequestParam(value = "tag", required = false) String name,
                                                     @RequestParam(value = "name", required = false) String regexName,
                                                     @RequestParam(value = "description", required = false) String regexDescription,
                                                     @RequestParam(value = "sort", required = false) String sort,
                                                     @RequestParam(value = "order", required = false) Order order){
        String regex = regexName == null ? regexDescription : regexName;
        String regexColumn = regexName == null ? "description" : "name";
        return certificateService.findByClause(name, regexColumn, regex, sort, order);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO create(@Valid @RequestBody GiftCertificateDTO certificateDTO){
        return certificateService.create(certificateDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
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
