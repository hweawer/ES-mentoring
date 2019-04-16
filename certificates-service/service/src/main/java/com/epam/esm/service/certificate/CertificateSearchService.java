package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.certificate.impl.SearchCertificateRequest;
import com.epam.esm.service.dto.PaginationDto;

public interface CertificateSearchService {
    CertificateDto findById(Long id);

    PaginationDto<CertificateDto> searchByClause(SearchCertificateRequest request);
}
