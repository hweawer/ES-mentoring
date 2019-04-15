package com.epam.esm.service.certificate;

import com.epam.esm.service.dto.CertificateDto;

public interface UpdateCertificateService {
    default CertificateDto putUpdate(Long id, CertificateDto dto){
        return update(id, dto);
    }

    default CertificateDto patchUpdate(Long id, CertificateDto dto){
        return update(id, dto);
    }

    CertificateDto update(Long id, CertificateDto dto);
}
