package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CertificatesRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<GiftCertificate> implements CertificatesRepository {

    public CertificateRepositoryImpl() {
        super(GiftCertificate.class);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        certificate.setModificationDate(LocalDate.now());
        return super.update(certificate);
    }

    @Override
    public void create(GiftCertificate certificate) {
        certificate.setCreationDate(LocalDate.now());
        super.create(certificate);
    }
}
