package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
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
    public List<GiftCertificateDTO> findAll(){
        return certificateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") Long id){
        return certificateService.findById(id);
    }

    @GetMapping(value = "/sort/name/{asc}")
    public List<GiftCertificateDTO> findSortedByName(@PathVariable("asc") Boolean asc){
        return certificateService.findSortedByName(asc);
    }

    @GetMapping(value = "/sort/date/{asc}")
    public List<GiftCertificateDTO> findSortedByDate(@PathVariable("asc") Boolean asc){
        return certificateService.findSortedByDate(asc);
    }

    @GetMapping(value = "/tag/{name}")
    public List<GiftCertificateDTO> findByTag(@PathVariable("name") String name){
        return certificateService.findByTag(name);
    }

    @GetMapping(value = "/tag/{name}/sort/date/{asc}")
    public List<GiftCertificateDTO> findByTagSortedByDate(@PathVariable("name") String name,
                                                          @PathVariable("asc") Boolean asc){
        return certificateService.findByTagSortedByDate(name, asc);
    }

    @GetMapping(value = "/tag/{name}/sort/name/{asc}")
    public List<GiftCertificateDTO> findByTagSortedByName(@PathVariable("name") String name,
                                                          @PathVariable("asc") Boolean asc){
        return certificateService.findByTagSortedByName(name, asc);
    }

    @GetMapping(value = "/name/{part}")
    public List<GiftCertificateDTO> findByNamePart(@PathVariable("part") String part){
        return certificateService.findByNamePart(part);
    }

    @GetMapping(value = "/name/{part}/sort/name/{asc}")
    public List<GiftCertificateDTO> findByNamePartSortedByName(@PathVariable("part") String part,
                                                   @PathVariable("asc") Boolean asc){
        return certificateService.findByNamePartSortedByName(part, asc);
    }

    @GetMapping(value = "/name/{part}/sort/date/{asc}")
    public List<GiftCertificateDTO> findByNamePart(@PathVariable("part") String part,
                                                   @PathVariable("asc") Boolean asc){
        return certificateService.findByNamePartSortedByDate(part, asc);
    }

    @GetMapping(value = "/tag/{name}/name/{part}/sort/date/{asc}")
    public List<GiftCertificateDTO> findByTagByNamePartSortedByDate(@PathVariable("name") String name,
                                                        @PathVariable("part") String part,
                                                        @PathVariable("asc") Boolean asc){
        return certificateService.findByTagByNamePartSortedByDate(name, part, asc);
    }

    @GetMapping(value = "/tag/{name}/name/{part}/sort/name/{asc}")
    public List<GiftCertificateDTO> findByTagByNamePartSortedByName(@PathVariable("name") String name,
                                                        @PathVariable("part") String part,
                                                        @PathVariable("asc") Boolean asc){
        return certificateService.findByTagByNamePartSortedByName(name, part, asc);
    }

    @GetMapping(value = "/tag/{name}/description/{part}/sort/name/{asc}")
    public List<GiftCertificateDTO> findByTagByDescriptionPartSortedByName(@PathVariable("name") String name,
                                                                    @PathVariable("part") String part,
                                                                    @PathVariable("asc") Boolean asc){
        return certificateService.findByTagByDescriptionPartSortedByName(name, part, asc);
    }

    @GetMapping(value = "/tag/{name}/description/{part}/sort/date/{asc}")
    public List<GiftCertificateDTO> findByTagByDescriptionPartSortedByDate(@PathVariable("name") String name,
                                                                           @PathVariable("part") String part,
                                                                           @PathVariable("asc") Boolean asc){
        return certificateService.findByTagByDescriptionPartSortedByDate(name, part, asc);
    }

    @GetMapping(value = "/description/{part}")
    public List<GiftCertificateDTO> findByDescriptionPart(@PathVariable("part") String part){
        return certificateService.findByDescriptionPart(part);
    }

    @GetMapping(value = "/description/{part}/sort/date/{asc}")
    public List<GiftCertificateDTO> findByDescriptionPartSortedByDate(@PathVariable("part") String part,
                                                          @PathVariable("asc") Boolean asc){
        return certificateService.findByDescriptionPartSortedByDate(part, asc);
    }

    @GetMapping(value = "/description/{part}/sort/name/{asc}")
    public List<GiftCertificateDTO> findByDescriptionPartSortedByName(@PathVariable("part") String part,
                                                                      @PathVariable("asc") Boolean asc){
        return certificateService.findByDescriptionPartSortedByName(part, asc);
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
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        certificateService.delete(id);
    }



}
