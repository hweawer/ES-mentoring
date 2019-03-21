package com.epam.esm.repository;

import com.epam.esm.config.DbColumns;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.config.DbColumns.*;

@Repository
public class CertificateRepository extends AbstractRepository<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger(CertificateRepository.class);

    public CertificateRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, certificateTable, id);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        logger.debug("CREATE CERTIFICATE: " + certificate);
        Objects.requireNonNull(certificate, "CERTIFICATE CREATE: Certificate is null");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(name,
                Objects.requireNonNull(certificate.getName(), "CREATE CERTIFICATE: Certificate name is null"));
        parameters.put(description,
                Objects.requireNonNull(certificate.getDescription(), "CREATE CERTIFICATE: Certificate description is null"));
        parameters.put(price,
                Objects.requireNonNull(certificate.getPrice(), "CREATE CERTIFICATE: Certificate price is null"));
        parameters.put(creationDate,
                Objects.requireNonNull(certificate.getCreationDate(), "CREATE CERTIFICATE: Creation date is null"));
        parameters.put(modificationDate, certificate.getModificationDate());
        parameters.put(duration,
                Objects.requireNonNull(certificate.getDuration(), "CREATE CERTIFICATE: Certificate duration is null"));
        Long id = certificate.getId();
        if (id != null){
            parameters.put(DbColumns.id, id);
        }
        Long insertedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        certificate.setId(insertedId);

        Set<Tag> tags = certificate.getTagSet();
        if(tags != null){
            SimpleJdbcInsert relationInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(relationTable);
            Map<String, Object> relationParameters = new HashMap<>();
            tags.forEach(tag -> {
                relationParameters.put(relationCertificateId, insertedId);
                relationParameters.put(relationTagId, tag.getId());
                relationInsert.execute(relationParameters);
            });
        }
        return certificate;
    }

    @Override
    public Integer remove(GiftCertificate certificate) {
        String DELETE_CERTIFICATE = "DELETE FROM " + certificateTable + " WHERE " + certificateId + "=?;";
        Objects.requireNonNull(certificate, "CERTIFICATE REMOVE: Certificate is null");
        Long id = certificate.getId();
        Objects.requireNonNull(id, "CERTIFICATE REMOVE: Certificate id is null");
        return remove(DELETE_CERTIFICATE, id);
    }

    @Override
    public Integer update(GiftCertificate certificate) {
        String UPDATE_CERTIFICATE = "UPDATE " + certificateTable + " SET " +
                certificateName + "=?, " +
                certificateDescription + "=?, " +
                certificatePrice + "=?, " +
                certificateCreationDate + "=?, " +
                certificateModificationDate + "=?, " +
                certificateDuration + " =?" +
                " WHERE " + certificateId + "=?";
        logger.debug("UPDATE CERTIFICATE: " + certificate);
        Object[] params = { certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreationDate(),
                certificate.getModificationDate(),
                certificate.getDuration(),
                certificate.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.DATE, Types.DATE, Types.INTEGER, Types.BIGINT};
        return jdbcTemplate.update(UPDATE_CERTIFICATE, params, types);
    }

    @Override
    public List<GiftCertificate> queryFromDatabase(Specification specification) {
        String sql = specification.toSqlClauses();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> queryFromDatabase(Collection<Specification> specification) {
        String sql = specification.stream()
                .map(Specification::toSqlClauses)
                .collect(Collectors.joining(" ")) + ";";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }
}
