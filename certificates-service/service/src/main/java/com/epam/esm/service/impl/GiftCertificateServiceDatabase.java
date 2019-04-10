package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.TagMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class GiftCertificateServiceDatabase implements GiftCertificateService {
    private CrudRepository<GiftCertificate> certificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateServiceDatabase(CrudRepository<GiftCertificate> certificateRepository,
                                          TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

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

    @Transactional(readOnly = true)
    @Override
    public List<CertificateDto> findAll(Integer page, Integer limit) {
        return certificateRepository.findAll(page, limit)
                .map(CertificateMapper.INSTANCE::toDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto dto) {
        GiftCertificate certificate = CertificateMapper.INSTANCE.toEntity(dto);
        return CertificateMapper.INSTANCE.toDto(certificateRepository.update(certificate));
    }

    //todo: localization message
    @Transactional
    @Override
    public void delete(Long id) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        certificateRepository.delete(certificate);
    }

    //todo: localization message
    @Transactional(readOnly = true)
    @Override
    public CertificateDto findById(Long id) {
        return CertificateMapper.INSTANCE.toDto(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("")));
    }

    //todo: localization message
    @Transactional
    @Override
    public CertificateDto patch(Long id, CertificateDto certificateDto) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        String name = certificateDto.getName();
        String description = certificateDto.getDescription();
        Short duration = certificateDto.getDuration();
        BigDecimal price = certificateDto.getPrice();
        Set<TagDto> tagDtos = certificateDto.getTags();
        if (name != null) certificate.setName(name);
        if (description != null) certificate.setDescription(description);
        if (duration != null) certificate.setDuration(duration);
        if (price != null) certificate.setPrice(price);
        if(tagDtos != null){
            Set<Tag> tags = tagDtos.stream()
                                .map(TagMapper.INSTANCE::toEntity)
                                .collect(toSet());
            certificate.setTags(tags);
        }
        return CertificateMapper.INSTANCE.toDto(certificateRepository.update(certificate));
    }

    @Override
    public List<CertificateDto> findByClause(Integer page, Integer limit,
            List<String> tags, String filterAttribute, String filterValue, String orderAttribute) {
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

        return certificateRepository.findAll(query, page, limit)
                .map(CertificateMapper.INSTANCE::toDto)
                .collect(toList());
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
