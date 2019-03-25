package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.Repository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public GiftCertificateServiceDatabase(Repository<GiftCertificate> certificateRepository,
                                          Repository<Tag> tagRepository, ModelMapper modelMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GiftCertificateDTO create(GiftCertificateDTO certificateDTO) {
        logger.debug("TAG SERVICE: create");
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
        GiftCertificate created = certificateRepository.create(certificate);
        return modelMapper.map(created, GiftCertificateDTO.class);
    }

    @Transactional
    @Override
    public Integer delete(Long id) {
        logger.debug("TAG SERVICE: delete");
        return certificateRepository.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GiftCertificateDTO> findAll() {
        List<GiftCertificate> tags = certificateRepository.findAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, GiftCertificateDTO.class))
                .collect(toList());
    }

    @Transactional
    @Override
    public Integer update(GiftCertificateDTO certificateDTO) {
        logger.debug("TAG SERVICE: create");
        GiftCertificate certificate = modelMapper.map(certificateDTO, GiftCertificate.class);
        return certificateRepository.update(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftCertificateDTO findById(Long id) throws EntityNotFoundException {
        List<GiftCertificate> selected = certificateRepository.findById(id);
        if (selected.isEmpty()){
            throw new EntityNotFoundException("No tag with such id.");
        }
        return modelMapper.map(selected.get(0), GiftCertificateDTO.class);
    }
}
