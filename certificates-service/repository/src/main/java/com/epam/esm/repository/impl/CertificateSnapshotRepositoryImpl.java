package com.epam.esm.repository.impl;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.SnapshotRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class CertificateSnapshotRepositoryImpl extends AbstractRepository<CertificateSnapshot> implements SnapshotRepository {
    public CertificateSnapshotRepositoryImpl() {
        super(CertificateSnapshot.class);
    }

    @Override
    public List<CertificateSnapshot> snapshotsByUser(Integer page, Integer limit, User user) {
        final String USER_SNAPSHOTS = "select c from CertificateSnapshot c where c.userId=:id";
        return entityManager.createQuery(USER_SNAPSHOTS, CertificateSnapshot.class)
                .setParameter("id", user.getId()).setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Long userSnapshotsCount(User user) {
        final String USER_SNAPSHOTS_COUNT = "select count(c) from CertificateSnapshot c where c.userId=:id";
        return entityManager.createQuery(USER_SNAPSHOTS_COUNT, Long.class)
                .setParameter("id", user.getId())
                .getSingleResult();
    }
}
