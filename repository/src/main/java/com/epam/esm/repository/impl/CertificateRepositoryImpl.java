package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<GiftCertificate> {

    public CertificateRepositoryImpl() {
        super(GiftCertificate.class);
    }
}
