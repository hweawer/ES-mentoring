package com.epam.esm.repository.impl;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateSnapshotRepositoryImpl extends AbstractRepository<CertificateSnapshot> {
    public CertificateSnapshotRepositoryImpl() {
        super(CertificateSnapshot.class);
    }
}
