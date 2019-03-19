package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.config.DbColumns.*;

public class CertificateRepository extends AbstractRepository<GiftCertificate> {
    private static final String DELETE_CERTIFICATE = "DELETE FROM " + certificateTable + " WHERE " + certificateId + "=?;";
    private static final String UPDATE_CERTIFICATE = "UPDATE " + certificateTable + " SET " +
            certificateName + "=?, " +
            certificateDescription + "=?, " +
            certificatePrice + "=?, " +
            creationDate + "=?, " +
            modificationDate + "=?, " +
            certificateDuration + " =?" +
            " WHERE " + certificateId + "=?";

    private static final Logger logger = LogManager.getLogger(CertificateRepository.class);

    public CertificateRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, certificateTable, certificateId);
    }

    @Override
    public Long create(GiftCertificate certificate) {
        logger.debug("CREATE CERTIFICATE: " + certificate);
        Objects.requireNonNull(certificate, "CERTIFICATE CREATE: Certificate is null");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(certificateName,
                Objects.requireNonNull(certificate.getName(), "CREATE CERTIFICATE: Certificate name is null"));
        parameters.put(certificateDescription,
                Objects.requireNonNull(certificate.getDescription(), "CREATE CERTIFICATE: Certificate description is null"));
        parameters.put(certificatePrice,
                Objects.requireNonNull(certificate.getPrice(), "CREATE CERTIFICATE: Certificate price is null"));
        parameters.put(creationDate,
                Objects.requireNonNull(certificate.getCreationDate(), "CREATE CERTIFICATE: Creation date is null"));
        parameters.put(modificationDate, certificate.getModificationDate());
        parameters.put(certificateDuration,
                Objects.requireNonNull(certificate.getDuration(), "CREATE CERTIFICATE: Certificate duration is null"));
        Long id = certificate.getId();
        if (id != null){
            parameters.put(certificateId, id);
        }
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Integer remove(GiftCertificate certificate) {
        Objects.requireNonNull(certificate, "CERTIFICATE REMOVE: Certificate is null");
        Long id = certificate.getId();
        Objects.requireNonNull(id, "CERTIFICATE REMOVE: Certificate id is null");
        return remove(DELETE_CERTIFICATE, id);
    }

    @Override
    public Integer update(GiftCertificate certificate) {
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
    public List<GiftCertificate> queryFromDatabase(Specification<GiftCertificate> specification) {
        return null;
    }

    @Override
    public List<GiftCertificate> queryFromCollection(Specification<GiftCertificate> specification) {
        return null;
    }
}
