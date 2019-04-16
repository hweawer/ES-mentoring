package com.epam.esm.service.certificate.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;

public abstract class AbstractJpaCriteriaCertificateRequestTranslator {
    protected void formSubquery(SearchCertificateRequest searchRequest, Root<GiftCertificate> from,
                    CriteriaQuery query, CriteriaBuilder builder){
        String filterValue = searchRequest.getValue();
        String filterAttribute = searchRequest.getColumn();
        String orderAttribute = searchRequest.getSort();
        List<String> tags = searchRequest.getTag();
        Predicate predicate = null;

        if (!tags.isEmpty()) {
            Subquery<Long> sub = query.subquery(Long.class);
            Root<GiftCertificate> sqFrom = sub.from(GiftCertificate.class);
            Join<GiftCertificate, Tag> join = sqFrom.join(GiftCertificate_.tags);

            sub.select(sqFrom.get(GiftCertificate_.id));
            sub.where(join.get(Tag_.name).in(tags)).groupBy(sqFrom.get(GiftCertificate_.id));
            sub.having(builder.equal(builder.count(sqFrom), tags.size()));
            predicate = builder.in(from.get(GiftCertificate_.id)).value(sub);
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
    }
}
