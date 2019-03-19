package com.epam.esm.repository.mapper;

import com.epam.esm.config.DbColumns;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import static com.epam.esm.config.DbColumns.*;

public class CertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getLong(id));
        certificate.setName(resultSet.getString(name));
        certificate.setDescription(resultSet.getString(description));
        certificate.setPrice(resultSet.getBigDecimal(price));
        certificate.setCreationDate(resultSet.getDate(creationDate).toLocalDate());
        Date modificationDate = resultSet.getDate(DbColumns.modificationDate);
        certificate.setModificationDate(modificationDate == null ? null : modificationDate.toLocalDate());
        certificate.setDuration(resultSet.getShort(duration));
        return certificate;
    }
}
