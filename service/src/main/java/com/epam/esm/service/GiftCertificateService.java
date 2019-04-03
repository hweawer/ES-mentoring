package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;

import java.util.List;

//todo : crud service interface
public interface GiftCertificateService {
    CertificateDto create(CertificateDto t);
    List<CertificateDto> findAll();
    void delete(Long id);
    CertificateDto findById(Long id);
}
