package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.certificate.DeleteCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class DeleteCertificateServiceImpl implements DeleteCertificateService {
    private CertificatesRepository certificatesRepository;

    @Transactional
    @Override
    public void deleteCertificate(Long id) {
        GiftCertificate certificate = certificatesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("tag.not.found"));
        certificatesRepository.delete(certificate);
    }
}
