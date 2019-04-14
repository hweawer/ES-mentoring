package com.epam.esm.service.delete.impl;

import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.delete.DeleteCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteCertificateServiceImpl implements DeleteCertificateService {
    @NonNull
    private CertificatesRepository certificatesRepository;

    @Transactional
    @Override
    public void deleteCertificate(Long id) {
        certificatesRepository.findById(id)
                .ifPresentOrElse(tag -> certificatesRepository.delete(tag), () -> new EntityNotFoundException("certificate.not.found"));
    }
}
