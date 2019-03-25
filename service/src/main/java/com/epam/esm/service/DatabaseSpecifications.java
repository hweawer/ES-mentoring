package com.epam.esm.service;

import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public final class DatabaseSpecifications {
    private static Logger logger = LogManager.getLogger();

    public static Specification findAllGifts(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName);
        logger.debug("SQL findAllGifts : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification findTagByName(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(TagTable.tableName)
                .where()
                .equal(TagTable.name);
        logger.debug("SQL findTagByName : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification tagsByCertificate(){
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
                .equal(CertificateTable.certificateId);
        logger.debug("SQL tagsByCertificate : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification findTagById(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(TagTable.tableName)
                .where()
                .equal(TagTable.id);
        logger.debug("SQL findTagById : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification findCertificateById(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .where()
                .equal(CertificateTable.id);
        logger.debug("SQL findCertificateById : " + builder.toSqlClauses());
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

    public static Specification certificatesByNamePart(){
        return byLike(CertificateTable.name);
    }

    public static Specification certificatesByDescriptionPart(){
        return byLike(CertificateTable.description);
    }

    public static Specification findAllTags(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(TagTable.tableName);
        logger.debug("SQL findAllTags : " + builder.toSqlClauses());
        return builder;
    }

    private static Specification byLike(String column) {
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .where()
                .equal(column);
        logger.debug("SQL byLike : " + builder.toSqlClauses());
        return builder;
    }
}
