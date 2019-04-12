package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.SearchCertificateRequest;
import com.epam.esm.service.SearchCertificateRequestTranslator;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.mapper.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class GiftCertificateServiceDatabase implements GiftCertificateService {
    @NonNull
    private CrudRepository<GiftCertificate> certificateRepository;
    @NonNull
    private TagRepository tagRepository;
    @NonNull
    private SearchCertificateRequestTranslator<CriteriaQuery<GiftCertificate>> criteriaTranslator;

    @Transactional
    @Override
    public CertificateDto create(CertificateDto certificateDTO) {
        GiftCertificate certificate = CertificateMapper.INSTANCE.toEntity(certificateDTO);
        Set<Tag> attachedTags = certificate.getTags().stream()
                .map(tag -> tagRepository.findTagByName(tag.getName()).orElse(tag))
                .collect(toSet());
        certificate.setTags(attachedTags);
        certificateRepository.create(certificate);
        return CertificateMapper.INSTANCE.toDto(certificate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        certificateRepository.delete(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public CertificateDto findById(Long id) {
        return CertificateMapper.INSTANCE.toDto(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("")));
    }


    @Transactional(readOnly = true)
    @Override
    public List<CertificateDto> searchByClause(SearchCertificateRequest searchRequest) {
        Integer page = searchRequest.getPage();
        Integer limit = searchRequest.getLimit();

        Double certificatesCount = Double.valueOf(certificateRepository.count());

        if (page > certificatesCount / limit){
            throw new IncorrectPaginationValues("");
        }
        CriteriaQuery<GiftCertificate> query = criteriaTranslator.translate(searchRequest);

        return certificateRepository.findAll(query, page, limit)
                .map(CertificateMapper.INSTANCE::toDto)
                .collect(toList());
    }
}
