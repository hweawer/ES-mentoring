package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.certificate.impl.SearchCertificateRequest;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.PaginationInfoDto;

public interface CertificateSearchService {
    CertificateDto findById(Long id);

    PaginationInfoDto<CertificateDto> searchByClause(SearchCertificateRequest request);
}
