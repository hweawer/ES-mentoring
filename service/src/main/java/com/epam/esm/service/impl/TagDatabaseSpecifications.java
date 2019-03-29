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
                .from(TagTable.TABLE_NAME)
                .where()
                .equivalent(TagTable.NAME, name);
        logger.debug("SQL findTagByName : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification findTagsByCertificate(GiftCertificate certificate){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.TABLE_NAME)
                .innerJoin(CertificateTagTable.TABLE_NAME,
                        CertificateTable.CERTIFICATE_ID,
                        CertificateTagTable.RELATION_CERTIFICATE_ID)
                .innerJoin(TagTable.TABLE_NAME,
                        CertificateTagTable.RELATION_TAG_ID,
                        TagTable.TAG_ID)
                .where()
                .equivalent(CertificateTable.CERTIFICATE_ID, certificate.getId());
        logger.debug("SQL findTagsByCertificate : " + builder.toSqlClauses());
        return builder;
    }
}
