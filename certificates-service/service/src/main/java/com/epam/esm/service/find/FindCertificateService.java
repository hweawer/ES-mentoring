package com.epam.esm.service.find;

import com.epam.esm.service.dto.CertificateDto;

import java.util.List;

public interface FindCertificateService {
    CertificateDto findById(Long id);

    List<CertificateDto> searchByClause(SearchCertificateRequest request);
}
