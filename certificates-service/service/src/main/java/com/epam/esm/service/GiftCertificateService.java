package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;

import java.util.List;

public interface GiftCertificateService {
    CertificateDto create(CertificateDto dto);

    CertificateDto update(CertificateDto dto);

    void delete(Long id);

    CertificateDto findById(Long id);

    CertificateDto patch(Long id, CertificateDto certificateDto);

    List<CertificateDto> findByClause(Integer page, Integer limit,
                                      List<String> tags, String filterAttribute, String filterValue, String orderAttribute);
}
