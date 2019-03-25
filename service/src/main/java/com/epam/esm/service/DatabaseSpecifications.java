package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DatabaseSpecifications {
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

    public static Specification tagsByCertificate(GiftCertificate certificate){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .innerJoin(CertificateTagTable.tableName,
                        CertificateTable.certificateId,
                        CertificateTagTable.relationCertificateId)
                .innerJoin(TagTable.tableName,
                        CertificateTagTable.relationCertificateTableId,
                        TagTable.tagId)
                .where()
                .equal(CertificateTable.certificateId, certificate.getId());
        logger.debug("SQL tagsByCertificate : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification certificatesSortedByDate(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .orderBy(CertificateTable.creationDate);
        logger.debug("SQL certificatesSortedByDate : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification certificatesSortedByName(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .orderBy(CertificateTable.name);
        logger.debug("SQL certificatesSortedByDate : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification certificatesByNamePart(String pattern){
        return byLike(CertificateTable.name, pattern);
    }

    public static Specification certificatesByDescriptionPart(String pattern){
        return byLike(CertificateTable.description, pattern);
    }

    private static Specification byLike(String column, String pattern) {
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .where()
                .like(column, pattern);
        logger.debug("SQL byLike : " + builder.toSqlClauses());
        return builder;
    }
}
