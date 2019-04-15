package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.certificate.impl.SearchCertificateRequest;

import java.util.List;

public interface FindCertificateService {
    CertificateDto findById(Long id);

    List<CertificateDto> searchByClause(SearchCertificateRequest request);
}
