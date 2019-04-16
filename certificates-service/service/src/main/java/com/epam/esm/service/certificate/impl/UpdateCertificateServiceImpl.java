package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.certificate.UpdateCertificateService;
import com.epam.esm.service.certificate.update.property.*;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UpdateCertificateServiceImpl implements UpdateCertificateService {
    private CertificatesRepository certificateRepository;
    private UpdateCertificateName updateName;
    private UpdateCertificateDescription updateDescription;
    private UpdateCertificatePrice updatePrice;
    private UpdateCertificateDuration updateDuration;
    private UpdateCertificateTags updateTags;

    @Transactional
    public CertificateDto update(Long id, CertificateDto dto, CertificateMapper mapper) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("certificate.not.found"));
        GiftCertificate updated = new GiftCertificate();
        updated.setName(certificate.getName());
        updated.setDuration(certificate.getDuration());
        updated.setDescription(certificate.getDescription());
        updated.setPrice(certificate.getPrice());
        updated.setTags(certificate.getTags());
        mapper.updateCertificateFromDto(dto, updated);
        updateName.update(certificate, updated.getName());
        updateDescription.update(certificate, updated.getDescription());
        updatePrice.update(certificate, updated.getPrice());
        updateDuration.update(certificate, updated.getDuration());
        updateTags.update(certificate, updated.getTags());

        return CertificateFullUpdateMapper.INSTANCE.toDto(certificateRepository.update(certificate));
    }
}
