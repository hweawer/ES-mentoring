package com.epam.esm.repository.repository;

import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.config.RepositoryConfig;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

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
        super(jdbcTemplate, CertificateTable.TABLE_NAME, CertificateTable.ID, RepositoryConfig.schemaInUse);
        this.giftMapper = giftMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE CREATE: Certificate is null");
        logger.debug("CREATE CERTIFICATE: " + certificate);

        final String INSERT_TAG = "INSERT INTO " + CertificateTable.TABLE_NAME +
                "(" + CertificateTable.NAME + ", " +
                CertificateTable.DESCRIPTION + ", " +
                CertificateTable.DURATION + ", " +
                CertificateTable.CREATION_DATE + ", " +
                CertificateTable.PRICE + ")"
                + " VALUES(?, ?, ?, ?, ?) ON CONFLICT (" + CertificateTable.ID + ") DO NOTHING RETURNING " + TagTable.ID;

        final String INSERT_DEPENDENCY = "INSERT INTO " + CertificateTagTable.TABLE_NAME +
                "(" + CertificateTagTable.RELATION_CERTIFICATE_ID + ", " +
                CertificateTagTable.RELATION_TAG_ID + ")"
                + " VALUES(?, ?)";
        Long insertedCertificateId = jdbcTemplate.queryForObject(INSERT_TAG,
                new Object[]{certificate.getName(), certificate.getDescription(), certificate.getDuration(),
                        certificate.getCreationDate(), certificate.getPrice()}, Long.class);
        certificate.setId(insertedCertificateId);

        Set<Tag> tags = certificate.getTags();
        tags.forEach(tag ->
                jdbcTemplate.update(INSERT_DEPENDENCY, insertedCertificateId, tag.getId())
        );
        return certificate;
    }

    @Override
    public Integer delete(Long id) {
        final String DELETE_CERTIFICATE = "DELETE FROM " + CertificateTable.TABLE_NAME
                + " WHERE " + CertificateTable.ID + "=?;";
        return delete(DELETE_CERTIFICATE, id);
    }

    @Override
    public Integer update(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE REMOVE: Certificate is null");
        final String UPDATE_CERTIFICATE = "UPDATE " + CertificateTable.TABLE_NAME + " SET " +
                CertificateTable.NAME + "=?, " +
                CertificateTable.DESCRIPTION + "=?, " +
                CertificateTable.PRICE + "=?, " +
                CertificateTable.MODIFICATION_DATE + "=?, " +
                CertificateTable.DURATION + " =?" +
                " WHERE " + CertificateTable.ID + "=?";
        logger.debug("UPDATE CERTIFICATE: " + certificate);
        Object[] params = { certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getModificationDate(),
                certificate.getDuration(),
                certificate.getId()};
        updateCertificateTagRelation(certificate);
        return jdbcTemplate.update(UPDATE_CERTIFICATE, params);
    }

    private void updateCertificateTagRelation(GiftCertificate certificate){
        final String SELECT_RELATION = "SELECT " + CertificateTagTable.RELATION_TAG_ID + " FROM " + CertificateTagTable.TABLE_NAME
                + " WHERE " + CertificateTagTable.RELATION_CERTIFICATE_ID + "=?";
        final String INSERT_DEPENDENCY = "INSERT INTO " + CertificateTagTable.TABLE_NAME +
                "(" + CertificateTagTable.RELATION_CERTIFICATE_ID + ", " +
                CertificateTagTable.RELATION_TAG_ID + ")"
                + " VALUES(?, ?)";
        List<Long> currentAssignedTagIds = jdbcTemplate.queryForList(SELECT_RELATION,
                new Object[]{certificate.getId()},
                Long.class);
        List<Long> targetAssignedTagIds = certificate.getTags().stream()
                .map(Tag::getId)
                .collect(toList());
        List<Long> removedTagIds = currentAssignedTagIds.stream()
                .filter(not(targetAssignedTagIds::contains))
                .collect(toList());
        List<Long> addedTagIds = targetAssignedTagIds.stream()
                .filter(not(currentAssignedTagIds::contains))
                .collect(toList());
        if(!removedTagIds.isEmpty()) {
            String deleteRelation = "DELETE FROM "
                    + CertificateTagTable.TABLE_NAME
                    + " WHERE " + RELATION_CERTIFICATE_ID + "=?"
                    + " AND " + RELATION_TAG_ID + " IN " + generatePlaceholders(removedTagIds.size());
            List<Long> params = new ArrayList<>();
            params.add(certificate.getId());
            params.addAll(removedTagIds);
            jdbcTemplate.update(deleteRelation, params.toArray(Object[]::new));
        }
        addedTagIds.forEach(id -> jdbcTemplate.update(INSERT_DEPENDENCY, certificate.getId(), id));
    }

    private String generatePlaceholders(Integer size){
        return Stream.generate(() -> "?")
                .limit(size)
                .collect(joining(",", "(", ")"));
    }

    @Override
    public List<GiftCertificate> queryFromDatabase(Specification specification) {
        SqlQuery sqlQuery = specification.toSqlClauses();
        return jdbcTemplate.query(sqlQuery.getSql(), sqlQuery.getParams(), giftMapper);
    }

    @Override
    public List<GiftCertificate> findAll() {
        final String SELECT_ALL = "SELECT * FROM " + CertificateTable.TABLE_NAME;
        return jdbcTemplate.query(SELECT_ALL, giftMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        final String SELECT_BY_ID = "SELECT * FROM " + CertificateTable.TABLE_NAME + " WHERE " + CertificateTable.ID + "=?";
        return jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, giftMapper).stream().findFirst();
    }
}
