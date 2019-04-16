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
public class JpaCriteriaSearchCertificatesTranslator extends AbstractJpaCriteriaCertificateRequestTranslator implements CertificateRequestTranslator<CriteriaQuery<GiftCertificate>> {
    private CrudRepository<GiftCertificate> certificateRepository;

    @Override
    public CriteriaQuery<GiftCertificate> translate(SearchCertificateRequest searchRequest) {
        CriteriaBuilder builder = certificateRepository.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> from = query.from(GiftCertificate.class);
        query.select(from);
        formSubquery(searchRequest, from, query, builder);
        return query;
    }
}
