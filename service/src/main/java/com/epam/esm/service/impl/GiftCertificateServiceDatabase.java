package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.esm.service.TagDatabaseSpecifications.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@Service
public class GiftCertificateServiceDatabase implements GiftCertificateService {
    private static Logger logger = LogManager.getLogger();
    private Repository<GiftCertificate> certificateRepository;
    private Repository<Tag> tagRepository;
    private final ModelMapper modelMapper;

    private static final Map<String, String> FILTER_COLUMN_TO_TABLE_COLUMN = Map.of(
            "name", CertificateTable.name,
            "description", CertificateTable.description,
            "date", CertificateTable.creationDate
    );

    public GiftCertificateServiceDatabase(Repository<GiftCertificate> certificateRepository,
                                          Repository<Tag> tagRepository, ModelMapper modelMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GiftCertificateDTO create(GiftCertificateDTO certificateDTO) {
        logger.debug("CERTIFICATE SERVICE: create");
        GiftCertificate certificate = modelMapper.map(certificateDTO, GiftCertificate.class);
        Set<Tag> tags = certificate.getTags();
        Set<Tag> notPresentTags = tags.stream()
                .filter(tag -> tagRepository.queryFromDatabase(
                        findTagByName(tag.getName())).isEmpty())
                .collect(toSet());
        tags.removeAll(notPresentTags);
        Set<Tag> certificateTags = notPresentTags.stream()
                .map(tagRepository::create)
                .collect(toSet());
        tags.stream()
                .map(tag -> tagRepository.queryFromDatabase(
                        findTagByName(tag.getName())).get(0))
                .forEach(certificateTags::add);
        certificate.setTags(certificateTags);
        certificate.setCreationDate(LocalDate.now());
        GiftCertificate created = certificateRepository.create(certificate);
        List<Tag> createdCertificateTags = tagRepository.queryFromDatabase(findTagsByCertificate(created));
        created.setTags(new HashSet<>(createdCertificateTags));
        return modelMapper.map(created, GiftCertificateDTO.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        logger.debug("CERTIFICATE SERVICE: delete");
        certificateRepository.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDTO certificateDTO) {
        logger.debug("CERTIFICATE SERVICE: update");
        GiftCertificate certificate = modelMapper.map(certificateDTO, GiftCertificate.class);
        certificate.setModificationDate(LocalDate.now());
        Set<Tag> tags = certificateDTO.getTags().stream()
                .map(tag -> tagRepository.queryFromDatabase(findTagByName(tag.getName())).get(0))
                .collect(toSet());
        certificate.setTags(tags);
        certificateRepository.update(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftCertificateDTO findById(Long id) throws EntityNotFoundException {
        logger.debug("CERTIFICATE SERVICE: findById");
        GiftCertificate certificate = certificateRepository.findById(id).stream()
                .findFirst()
                .map(this::eagerLoad)
                .orElseThrow(() -> new EntityNotFoundException("certificate.not.found.by.id"));
        return modelMapper.map(certificate, GiftCertificateDTO.class);
    }

    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findByClause(String tag,
                                                 String filterColumn,
                                                 String filterValue,
                                                 String orderColumn){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select(CertificateTable.certificateId,
                       CertificateTable.certificateName,
                       CertificateTable.certificateDescription,
                       CertificateTable.certificatePrice,
                       CertificateTable.certificateCreationDate,
                       CertificateTable.certificateModificationDate,
                       CertificateTable.certificateDuration)
                .from(CertificateTable.tableName);

        List<GiftCertificate> certificates;
        if(tag != null){
            builder.innerJoin(CertificateTagTable.tableName,
                              CertificateTable.certificateId,
                              CertificateTagTable.relationCertificateId)
                    .innerJoin(TagTable.tableName,
                               CertificateTagTable.relationTagId,
                               TagTable.tagId);
            builder.where();
            builder.equal(TagTable.tagName, tag);
        }
        if (filterValue != null){
            if (tag != null){
                builder.and();
            } else {
                builder.where();
            }
            builder.like(FILTER_COLUMN_TO_TABLE_COLUMN.get(filterColumn), "%" + filterValue + "%");
        }
        if(orderColumn != null){
            boolean desc = orderColumn.charAt(0) == '-';
            String filter = desc ? FILTER_COLUMN_TO_TABLE_COLUMN.get(orderColumn.substring(1))
                                : FILTER_COLUMN_TO_TABLE_COLUMN.get(orderColumn);
            builder.orderBy(filter);
            if(desc){
                builder.desc();
            }
        }
        certificates = certificateRepository.queryFromDatabase(builder);
        certificates.forEach(this::eagerLoad);
        return certificates.stream()
                .map(certificate -> modelMapper.map(certificate, GiftCertificateDTO.class))
                .collect(toList());
    }

    private GiftCertificate eagerLoad(GiftCertificate certificate) {
        Set<Tag> tags = new HashSet<>(tagRepository.queryFromDatabase(findTagsByCertificate(certificate)));
        certificate.setTags(tags);
        return certificate;
    }
}
