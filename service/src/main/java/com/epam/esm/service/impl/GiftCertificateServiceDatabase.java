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

import java.math.BigDecimal;
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
    public List<CertificateDto> findAll(Integer page, Integer limit) {
        return certificateRepository.findAll(page, limit)
                .map(CertificateMapper.INSTANCE::certificateToCertificateDto)
                .collect(toList());
    }

    @Override
    public CertificateDto update(CertificateDto dto) {
        GiftCertificate certificate = CertificateMapper.INSTANCE.certificateDtoToCertificate(dto);
        return CertificateMapper.INSTANCE.certificateToCertificateDto(certificateRepository.update(certificate));
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

    //todo: localization message
    @Transactional
    @Override
    public CertificateDto updateCost(Long id, BigDecimal price) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        certificate.setPrice(price);
        return CertificateMapper.INSTANCE.certificateToCertificateDto(certificateRepository.update(certificate));
    }

    @Override
    public List<CertificateDto> findByClause(Integer page, Integer limit,
            String tag, String filterAttribute, String filterValue, String orderAttribute) {
        /*SpecificationBuilder builder = new SpecificationBuilder();
        builder.select(CertificateTable.CERTIFICATE_ID,
                CertificateTable.CERTIFICATE_NAME,
                CertificateTable.CERTIFICATE_DESCRIPTION,
                CertificateTable.CERTIFICATE_PRICE,
                CertificateTable.CERTIFICATE_CREATION_DATE,
                CertificateTable.CERTIFICATE_MODIFICATION_DATE,
                CertificateTable.CERTIFICATE_DURATION)
                .from(CertificateTable.TABLE_NAME);

        List<GiftCertificate> certificates;
        if(tag != null){
            builder.innerJoin(CertificateTagTable.TABLE_NAME,
                    CertificateTable.CERTIFICATE_ID,
                    CertificateTagTable.RELATION_CERTIFICATE_ID)
                    .innerJoin(TagTable.TABLE_NAME,
                            CertificateTagTable.RELATION_TAG_ID,
                            TagTable.TAG_ID)
                    .where()
                    .equivalent(TagTable.TAG_NAME, tag);
        }
        if (filterValue != null){
            if (tag != null){
                builder.and();
            } else {
                builder.where();
            }
            String filterColumn = TABLE_COLUMN_BY_ATTRIBUTE.get(filterAttribute);
            if (filterColumn == null){
                throw new AliasNotFoundException("unknown.filter.attribute");
            }
            builder.like(filterColumn, "%" + filterValue + "%");
        }
        if(orderAttribute != null){
            boolean desc = orderAttribute.charAt(0) == '-';
            String order = desc ? TABLE_COLUMN_BY_ATTRIBUTE.get(orderAttribute.substring(1))
                    : TABLE_COLUMN_BY_ATTRIBUTE.get(orderAttribute);
            if(order == null){
                throw new AliasNotFoundException("unknown.sort.attribute");
            }
            builder.orderBy(order, desc);
        }
        certificates = certificateRepository.queryFromDatabase(builder);
        certificates.forEach(this::loadTags);
        return certificates.stream()
                .map(certificate -> modelMapper.map(certificate, GiftCertificateDTO.class))
                .collect(toList());*/
        return null;
    }

}
