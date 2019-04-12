package com.epam.esm.service.find.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificatesRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.service.find.SearchCertificateRequest;
import com.epam.esm.service.find.SearchCertificateRequestTranslator;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.mapper.CertificateMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.find.FindCertificateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class FindCertificateServiceImpl implements FindCertificateService {
    @NonNull
    private CertificatesRepository certificateRepository;
    @NonNull
    private SearchCertificateRequestTranslator<CriteriaQuery<GiftCertificate>> criteriaTranslator;

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
