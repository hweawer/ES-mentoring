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

import static com.epam.esm.repository.config.CertificateTable.*;
import static com.epam.esm.repository.config.CertificateTagTable.*;
import static java.util.stream.Collectors.*;

@Repository
public class CertificateRepository extends AbstractRepository<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger(CertificateRepository.class);

    private final BeanPropertyRowMapper<GiftCertificate> giftMapper;
    @Autowired
    public CertificateRepository(JdbcTemplate jdbcTemplate, BeanPropertyRowMapper<GiftCertificate> giftMapper, BeanPropertyRowMapper<Tag> tagMapper) {
        super(jdbcTemplate, CertificateTable.tableName, CertificateTable.id);
        this.giftMapper = giftMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE CREATE: Certificate is null");
        logger.debug("CREATE CERTIFICATE: " + certificate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(name, certificate.getName());
        parameters.put(description, certificate.getDescription());
        parameters.put(price, certificate.getPrice());
        parameters.put(creationDate, certificate.getCreationDate());
        parameters.put(modificationDate, certificate.getModificationDate());
        parameters.put(duration, certificate.getDuration());

        Long insertedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        certificate.setId(insertedId);

        Set<Tag> tags = certificate.getTags();
        if(!tags.isEmpty()){
            Map<String, Object> relationParameters = new HashMap<>();
            tags.forEach(tag -> {
                SimpleJdbcInsert relationInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName(CertificateTagTable.tableName);
                relationParameters.put(relationCertificateId, insertedId);
                relationParameters.put(relationTagId, tag.getId());
                relationInsert.execute(relationParameters);
            });
        }
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
        manageUpdateRelation(certificate);
        return jdbcTemplate.update(UPDATE_CERTIFICATE, params);
    }

    private void manageUpdateRelation(GiftCertificate certificate){
        final String SELECT_RELATION = "SELECT " + CertificateTagTable.relationTagId + " FROM " + CertificateTagTable.tableName
                + " WHERE " + CertificateTagTable.relationCertificateId + "=?";
        List<Long> relations = jdbcTemplate.queryForList(SELECT_RELATION,
                new Object[]{certificate.getId()},
                Long.class);
        List<Long> tagIds = certificate.getTags().stream()
                .map(Tag::getId)
                .collect(toList());
        List<Long> deleteIds = relations.stream()
                .filter(id -> !tagIds.contains(id))
                .collect(toList());
        List<Long> insertIds = tagIds.stream()
                .filter(id -> !relations.contains(id))
                .collect(toList());
        final String DELETE_RELATION = "DELETE FROM "
                + CertificateTagTable.tableName
                + " WHERE " + relationCertificateId + "=?"
                + " AND "+ relationTagId + "=?";
        deleteIds.forEach(id -> jdbcTemplate.update(DELETE_RELATION, new Object[]{certificate.getId(), id}));
        Map<String, Object> relationParameters = new HashMap<>();
        insertIds.forEach(id -> {
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
