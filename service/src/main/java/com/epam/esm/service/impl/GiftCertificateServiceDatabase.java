package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class GiftCertificateServiceDatabase implements GiftCertificateService {
    private CrudRepository<GiftCertificate> certificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateServiceDatabase(CrudRepository<GiftCertificate> certificateRepository,
                                          TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public CertificateDto create(CertificateDto certificateDTO) {
        GiftCertificate certificate = CertificateMapper.INSTANCE.certificateDtoToCertificate(certificateDTO);
        certificate.setCreationDate(LocalDate.now());
        Set<Tag> attachedTags = certificate.getTags().stream()
                .map(tag -> tagRepository.findTagByName(tag.getName()).orElse(tag))
                .collect(toSet());
        certificate.setTags(attachedTags);
        certificateRepository.create(certificate);
        return CertificateMapper.INSTANCE.certificateToCertificateDto(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CertificateDto> findAll() {
        return certificateRepository.findAll()
                .map(CertificateMapper.INSTANCE::certificateToCertificateDto)
                .collect(toList());
    }

    //todo: localization message
    @Transactional
    @Override
    public void delete(Long id) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        certificateRepository.delete(certificate);
    }

    //todo: localization message
    @Transactional(readOnly = true)
    @Override
    public CertificateDto findById(Long id) {
        return CertificateMapper.INSTANCE.certificateToCertificateDto(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("")));
    }

}
