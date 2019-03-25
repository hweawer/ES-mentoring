package com.epam.esm.repository.repository;

import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

import static com.epam.esm.repository.config.CertificateTable.*;
import static com.epam.esm.repository.config.CertificateTagTable.*;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

@Repository
public class CertificateRepository extends AbstractRepository<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger(CertificateRepository.class);

    private final BeanPropertyRowMapper<GiftCertificate> giftMapper;
    @Autowired
    public CertificateRepository(JdbcTemplate jdbcTemplate,
                                 BeanPropertyRowMapper<GiftCertificate> giftMapper) {
        super(jdbcTemplate, CertificateTable.tableName, CertificateTable.id);
        this.giftMapper = giftMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE CREATE: Certificate is null");
        logger.debug("CREATE CERTIFICATE: " + certificate);
        Map<String, Object> parameters = Map.of(
                name, certificate.getName(),
                description, certificate.getDescription(),
                price, certificate.getPrice(),
                creationDate, certificate.getCreationDate(),
                modificationDate, certificate.getModificationDate(),
                duration, certificate.getDuration()
        );

        Long insertedCertificateId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        certificate.setId(insertedCertificateId);

        Set<Tag> tags = certificate.getTags();
        tags.forEach(tag -> new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(CertificateTagTable.tableName)
                    .execute(Map.of(
                        relationCertificateId, insertedCertificateId,
                        relationTagId, tag.getId()
                        ))
        );
        return certificate;
    }

    @Override
    public Integer delete(Long id) {
        final String DELETE_CERTIFICATE = "DELETE FROM " + CertificateTable.tableName
                + " WHERE " + CertificateTable.id + "=?;";
        return remove(DELETE_CERTIFICATE, id);
    }

    @Override
    public Integer update(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE REMOVE: Certificate is null");
        final String UPDATE_CERTIFICATE = "UPDATE " + CertificateTable.tableName + " SET " +
                CertificateTable.name + "=?, " +
                CertificateTable.description + "=?, " +
                CertificateTable.price + "=?, " +
                CertificateTable.creationDate + "=?, " +
                CertificateTable.modificationDate + "=?, " +
                CertificateTable.duration + " =?" +
                " WHERE " + CertificateTable.id + "=?";
        logger.debug("UPDATE CERTIFICATE: " + certificate);
        Object[] params = { certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreationDate(),
                certificate.getModificationDate(),
                certificate.getDuration(),
                certificate.getId()};
        updateCertificateTagRelation(certificate);
        return jdbcTemplate.update(UPDATE_CERTIFICATE, params);
    }

    private void updateCertificateTagRelation(GiftCertificate certificate){
        final String SELECT_RELATION = "SELECT " + CertificateTagTable.relationTagId + " FROM " + CertificateTagTable.tableName
                + " WHERE " + CertificateTagTable.relationCertificateId + "=?";
        List<Long> currentAssignedTagIds = jdbcTemplate.queryForList(SELECT_RELATION,
                new Object[]{certificate.getId()},
                Long.class);
        List<Long> targetAssignedTagIds = certificate.getTags().stream()
                .map(Tag::getId)
                .collect(toList());
        List<Long> deleteIds = currentAssignedTagIds.stream()
                .filter(not(targetAssignedTagIds::contains))
                .collect(toList());
        List<Long> insertedCertificateIds = targetAssignedTagIds.stream()
                .filter(not(currentAssignedTagIds::contains))
                .collect(toList());
        String deleteRelation = "DELETE FROM "
                + CertificateTagTable.tableName
                + " WHERE " + relationCertificateId + " IN (";
        String params = Stream.generate(() -> "?")
                .limit(deleteIds.size())
                .collect(joining(",", "", ")"));
        deleteRelation += params;
        jdbcTemplate.update(deleteRelation, deleteIds.toArray(Object[]::new));
        Map<String, Object> relationParameters = new HashMap<>();
        insertedCertificateIds.forEach(id -> {
            SimpleJdbcInsert relationInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(CertificateTagTable.tableName);
            relationParameters.put(relationCertificateId, certificate.getId());
            relationParameters.put(relationTagId, id);
            relationInsert.execute(relationParameters);
        });
    }

    @Override
    public List<GiftCertificate> queryFromDatabase(Specification specification) {
        SqlQuery sqlQuery = specification.toSqlClauses();
        return jdbcTemplate.query(sqlQuery.getSql(), sqlQuery.getParams(), giftMapper);
    }

    @Override
    public List<GiftCertificate> findAll() {
        final String SELECT_ALL = "SELECT * FROM " + CertificateTable.tableName;
        return jdbcTemplate.query(SELECT_ALL, giftMapper);
    }

    @Override
    public List<GiftCertificate> findById(Long id) {
        final String SELECT_BY_ID = "SELECT * FROM " + CertificateTable.tableName + " WHERE " + CertificateTable.id + "=?";
        return jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, giftMapper);
    }
}
