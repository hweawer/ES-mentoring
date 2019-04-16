package com.epam.esm.repository;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.User;

import java.util.List;

public interface SnapshotRepository extends CrudRepository<CertificateSnapshot> {
    List<CertificateSnapshot> snapshotsByUser(Integer page, Integer limit, User user);
    Long userSnapshotsCount(User user);
}
