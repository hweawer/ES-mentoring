package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateCertificateService {
    CertificateDto update(Long id, CertificateDto dto, CertificateMapper mapper);
}
