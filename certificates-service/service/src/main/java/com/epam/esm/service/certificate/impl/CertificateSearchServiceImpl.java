package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.service.certificate.CertificateRequestTranslator;
import com.epam.esm.service.certificate.CertificateSearchService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.mapper.CertificateFullUpdateMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class CertificateSearchServiceImpl implements CertificateSearchService {
    private CertificatesRepository certificateRepository;
    private CertificateRequestTranslator<CriteriaQuery<GiftCertificate>> searchCriteriaTranslator;
    private CertificateRequestTranslator<CriteriaQuery<Long>> countCriteriaTranslator;

    @Transactional(readOnly = true)
    @Override
    public CertificateDto findById(Long id) {
        return CertificateFullUpdateMapper.INSTANCE.toDto(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("certificate.not.found")));
    }


    @Transactional(readOnly = true)
    @Override
    public PaginationDto<CertificateDto> searchByClause(SearchCertificateRequest searchRequest) {
        Integer page = searchRequest.getPage();
        Integer limit = searchRequest.getLimit();
        CriteriaQuery<GiftCertificate> queryForCertificates = searchCriteriaTranslator.translate(searchRequest);
        CriteriaQuery<Long> queryForCount = countCriteriaTranslator.translate(searchRequest);
        Double certificateCount = certificateRepository.count(queryForCount).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(certificateCount / limit)).intValue();
        if (page > pageCount && pageCount != 0) {
            throw new IncorrectPaginationValues("incorrect.pagination");
        }

        List<CertificateDto> certificates = certificateRepository.findAll(queryForCertificates, page, limit)
                .map(CertificateFullUpdateMapper.INSTANCE::toDto)
                .collect(toList());
        PaginationDto<CertificateDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(certificates);
        String path = "/certificates?";
        if (!searchRequest.getTag().isEmpty() || searchRequest.getColumn() != null && searchRequest.getSort() != null) {
            if (searchRequest.getColumn() != null) {
                path += "column=" + searchRequest.getValue() + "&";
            }
            if (searchRequest.getSort() != null) {
                path += "sort=" + searchRequest.getSort() + "&";
            }
            if (!searchRequest.getTag().isEmpty()) {
                String tags = searchRequest.getTag().stream()
                        .map(name -> "tag=" + name)
                        .collect(Collectors.joining("&", "", "&"));
                path += tags;
            }
        }
        paginationDto.setFirst(path + "page=1&limit=" + limit);
        paginationDto.setLast(path + "page=" + (pageCount == 0 ? 1 : pageCount) + "&limit=" + limit);
        String previous = page == 1 ? null : "/certificates?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount == 0 ? 1 : pageCount) ? null : "/certificates?page=" + (page + 1) + "&limit=" + limit;
        paginationDto.setPrevious(previous);
        paginationDto.setNext(next);

        return paginationDto;
    }
}
