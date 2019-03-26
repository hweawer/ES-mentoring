package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.esm.service.CertificateDatabaseSpecification.*;
import static com.epam.esm.service.TagDatabaseSpecifications.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@Service
public class GiftCertificateServiceDatabase implements GiftCertificateService {
    private static Logger logger = LogManager.getLogger();
    private Repository<GiftCertificate> certificateRepository;
    private Repository<Tag> tagRepository;
    private final ModelMapper modelMapper;

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
    public Integer delete(Long id) {
        logger.debug("CERTIFICATE SERVICE: delete");
        return certificateRepository.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateDTO> findAll() {
        logger.debug("CERTIFICATE SERVICE: findAll");
        List<GiftCertificate> certificates = certificateRepository.findAll();
        certificates.forEach(this::eager);
        return certificates.stream()
                .map(certificate -> modelMapper.map(certificate, GiftCertificateDTO.class))
                .collect(toList());
    }

    @Transactional
    @Override
    public Integer update(GiftCertificateDTO certificateDTO) {
        logger.debug("CERTIFICATE SERVICE: update");
        GiftCertificate certificate = modelMapper.map(certificateDTO, GiftCertificate.class);
        certificate.setModificationDate(LocalDate.now());
        Set<Tag> tags = certificateDTO.getTags().stream()
                .map(tag -> tagRepository.queryFromDatabase(findTagByName(tag.getName())).get(0))
                .collect(toSet());
        certificate.setTags(tags);
        return certificateRepository.update(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftCertificateDTO findById(Long id) throws EntityNotFoundException {
        logger.debug("CERTIFICATE SERVICE: findById");
        List<GiftCertificate> selected = certificateRepository.findById(id);
        if (selected.isEmpty()){
            throw new EntityNotFoundException("No tag with such id.");
        }
        GiftCertificate certificate = selected.get(0);
        eager(certificate);
        return modelMapper.map(certificate, GiftCertificateDTO.class);
    }

    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findByClause(String tag,
                                                 String regexColumn,
                                                 String part,
                                                 String orderColumn,
                                                 Order order){
        SpecificationBuilder builder = new SpecificationBuilder();
        selectFromCertificate(builder);
        List<GiftCertificate> certificates;
        if(tag != null){
            innerJoinTags(builder);
            builder.where();
            whereTag(builder, tag);
        }
        if (part != null){
            switch (regexColumn){
                case "name":
                    if(tag != null){
                        builder.and();
                    } else {
                        builder.where();
                    }
                    findByNamePart(builder, "%" + part + "%");
                    break;
                case "description":
                    if(tag != null){
                        builder.and();
                    } else {
                        builder.where();
                    }
                    findByDescriptionPart(builder, "%" + part + "%");
                    break;
            }
        }
        if(order != null){
            switch (orderColumn){
                case "name":
                    orderByName(builder, order);
                    break;
                case "date":
                    orderByDate(builder, order);
                    break;
            }
        }
        certificates = certificateRepository.queryFromDatabase(builder);
        certificates.forEach(this::eager);
        return certificates.stream()
                .map(certificate -> modelMapper.map(certificate, GiftCertificateDTO.class))
                .collect(toList());
    }

    private void eager(GiftCertificate certificate) {
        Set<Tag> tags = new HashSet<>(tagRepository.queryFromDatabase(findTagsByCertificate(certificate)));
        certificate.setTags(tags);
    }
}
