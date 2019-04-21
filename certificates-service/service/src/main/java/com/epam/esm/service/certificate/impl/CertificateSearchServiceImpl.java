package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.certificate.CertificateRequestTranslator;
import com.epam.esm.service.certificate.CertificateSearchService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageInfo;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class CertificateSearchServiceImpl implements CertificateSearchService {
    private final CertificatesRepository certificateRepository;
    private final CertificateRequestTranslator<CriteriaQuery<GiftCertificate>> searchCriteriaTranslator;
    private final CertificateRequestTranslator<CriteriaQuery<Long>> countCriteriaTranslator;

    @Transactional(readOnly = true)
    @Override
    public CertificateDto findById(Long id) {
        return CertificateFullUpdateMapper.INSTANCE.toDto(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("certificate.not.found")));
    }


    @Transactional(readOnly = true)
    @Override
    public PaginationInfoDto<CertificateDto> searchByClause(SearchCertificateRequest searchRequest) {
        Integer page = searchRequest.getPage();
        Integer limit = searchRequest.getLimit();

        CriteriaQuery<GiftCertificate> searchQuery = searchCriteriaTranslator.translate(searchRequest);
        CriteriaQuery<Long> countQuery = countCriteriaTranslator.translate(searchRequest);
        Double certificateCount = certificateRepository.count(countQuery).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(certificateCount / limit)).intValue();
        ValidationUtil.checkPagination(page, pageCount);

        List<CertificateDto> certificates = certificateRepository.findAll(searchQuery, page, limit)
                .map(CertificateFullUpdateMapper.INSTANCE::toDto)
                .collect(toList());

        return new PaginationInfoDto<>(certificates, new PageInfo(pageCount));
    }
}
