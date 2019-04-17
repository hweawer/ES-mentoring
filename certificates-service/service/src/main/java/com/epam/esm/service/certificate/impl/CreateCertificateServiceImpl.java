package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.certificate.CreateCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class CreateCertificateServiceImpl implements CreateCertificateService {
    @NonNull
    private TagRepository tagRepository;
    @NonNull
    private CertificatesRepository certificateRepository;

    @Transactional
    @Override
    public CertificateDto createCertificate(CertificateDto dto) {
        GiftCertificate certificate = CertificateFullUpdateMapper.INSTANCE.toEntity(dto);
        if(certificate.getTags() != null) {
            Set<Tag> attachedTags = certificate.getTags().stream()
                    .map(tag -> tagRepository.findTagByName(tag.getName()).orElse(tag))
                    .collect(toSet());
            certificate.setTags(attachedTags);
        } else {
            certificate.setTags(new HashSet<>());
        }
        certificateRepository.create(certificate);
        return CertificateFullUpdateMapper.INSTANCE.toDto(certificate);
    }
}