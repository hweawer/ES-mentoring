package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;

import java.util.List;

public interface GiftCertificateService {
    CertificateDto create(CertificateDto dto);

    void delete(Long id);

    CertificateDto findById(Long id);

    List<CertificateDto> searchByClause(SearchCertificateRequest request);
}
