package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.service.SearchCertificateRequest;
import com.epam.esm.service.SearchCertificateRequestTranslator;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JpaCriteriaSearchCertificateRequestTranslator implements SearchCertificateRequestTranslator<CriteriaQuery<GiftCertificate>> {
    @NonNull
    public CrudRepository<GiftCertificate> certificateRepository;

    @Override
    public CriteriaQuery<GiftCertificate> translate(SearchCertificateRequest searchRequest) {
        String filterValue = searchRequest.getValue();
        String filterAttribute = searchRequest.getColumn();
        String orderAttribute = searchRequest.getSort();
        List<String> tags = searchRequest.getTag();

        CriteriaBuilder builder = certificateRepository.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> from = query.from(GiftCertificate.class);
        query.select(from);
        Predicate predicate = null;
        if (!tags.isEmpty()) {
            predicate = tagsInPredicate(builder, query, tags, from);
        }
        if (filterValue != null){
            Predicate likePredicate = builder.like(from.get(filterAttribute), "%"+filterValue+"%");
            predicate = predicate == null ? likePredicate : builder.and(predicate, likePredicate);
        }
        if (predicate != null) {
            query.where(predicate);
        }
        if (orderAttribute != null){
            char order = orderAttribute.charAt(0);
            if (order == '-'){
                orderAttribute = orderAttribute.substring(1);
                query.orderBy(builder.desc(from.get(orderAttribute)));
            } else {
                query.orderBy(builder.asc(from.get(orderAttribute)));
            }
        }
        return query;
    }

    private Predicate tagsInPredicate(CriteriaBuilder builder,
                                      CriteriaQuery query,
                                      List<String> tags, Root<GiftCertificate> from){
        Subquery<Long> sub = query.subquery(Long.class);
        Root<GiftCertificate> sqFrom = sub.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> join = sqFrom.join(GiftCertificate_.tags);

        sub.select(sqFrom.get(GiftCertificate_.id));
        sub.where(join.get(Tag_.name).in(tags)).groupBy(sqFrom.get(GiftCertificate_.id));
        sub.having(builder.equal(builder.count(sqFrom), tags.size()));
        return builder.in(from.get(GiftCertificate_.id)).value(sub);
    }
}
