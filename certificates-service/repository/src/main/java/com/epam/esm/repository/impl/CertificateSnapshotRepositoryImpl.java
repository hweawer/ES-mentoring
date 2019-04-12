package com.epam.esm.repository.impl;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.SnapshotRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateSnapshotRepositoryImpl extends AbstractRepository<CertificateSnapshot> implements SnapshotRepository {
    public CertificateSnapshotRepositoryImpl() {
        super(CertificateSnapshot.class);
    }
}
