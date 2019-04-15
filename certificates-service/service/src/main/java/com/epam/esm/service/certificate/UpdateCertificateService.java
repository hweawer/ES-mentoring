package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateCertificateService {

    @Transactional
    default CertificateDto merge(Long id, CertificateDto dto){
        return update(id, dto);
    }

    CertificateDto update(Long id, CertificateDto dto);
}
