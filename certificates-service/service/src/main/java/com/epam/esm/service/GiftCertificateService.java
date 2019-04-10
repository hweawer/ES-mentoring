package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;

import java.math.BigDecimal;
import java.util.List;

public interface GiftCertificateService {
    CertificateDto create(CertificateDto dto);

    List<CertificateDto> findAll(Integer page, Integer limit);

    CertificateDto update(CertificateDto dto);

    void delete(Long id);

    CertificateDto findById(Long id);

    CertificateDto patch(Long id, CertificateDto certificateDto);

    List<CertificateDto> findByClause(Integer page, Integer limit,
                                      List<String> tags, String filterAttribute, String filterValue, String orderAttribute);
}
