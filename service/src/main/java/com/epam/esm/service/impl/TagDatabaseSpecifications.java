package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class TagDatabaseSpecifications {
    private static Logger logger = LogManager.getLogger();

    public static Specification findTagByName(String name){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(TagTable.tableName)
                .where()
                .equal(TagTable.name, name);
        logger.debug("SQL findTagByName : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification findTagsByCertificate(GiftCertificate certificate){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .innerJoin(CertificateTagTable.tableName,
                        CertificateTable.certificateId,
                        CertificateTagTable.relationCertificateId)
                .innerJoin(TagTable.tableName,
                        CertificateTagTable.relationTagId,
                        TagTable.tagId)
                .where()
                .equal(CertificateTable.certificateId, certificate.getId());
        logger.debug("SQL findTagsByCertificate : " + builder.toSqlClauses());
        return builder;
    }
}
