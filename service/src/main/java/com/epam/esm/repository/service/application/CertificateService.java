package com.epam.esm.service.application;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.Collection;
import java.util.List;

public interface CertificateService extends BasicService<GiftCertificate> {
    List<GiftCertificate> findByTag(Tag tag);
    List<GiftCertificate> findByTags(Collection<Tag> tags);
    List<GiftCertificate> findByNamePart(String part);
    List<GiftCertificate> findByNamePart(String part, boolean eager);
    List<GiftCertificate> findByDescriptionPart(String part);
    List<GiftCertificate> findByDescriptionPart(String part, boolean eager);
    List<GiftCertificate> findSortedByDate();
    List<GiftCertificate> findSortedByDate(boolean eager);
    List<GiftCertificate> findSortedByName();
    List<GiftCertificate> findSortedByName(boolean eager);
    List<GiftCertificate> findAll(boolean eager);
    GiftCertificate findById(Long id, boolean eager);
}
