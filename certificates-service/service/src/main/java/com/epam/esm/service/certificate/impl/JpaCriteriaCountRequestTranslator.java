package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.service.certificate.CertificateRequestTranslator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
@AllArgsConstructor
public class JpaCriteriaCountRequestTranslator extends AbstractJpaCriteriaCertificateRequestTranslator implements CertificateRequestTranslator<CriteriaQuery<Long>> {
    private CrudRepository<GiftCertificate> certificateRepository;

    @Override
    public CriteriaQuery<Long> translate(SearchCertificateRequest searchRequest) {
        CriteriaBuilder builder = certificateRepository.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GiftCertificate> from = query.from(GiftCertificate.class);
        query.select(builder.count(from));
        formSubquery(searchRequest, from, query, builder);
        return query;
    }
}
