package com.epam.esm.service;

import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.Specification;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CertificateDatabaseSpecification {
    private static Logger logger = LogManager.getLogger();

    public static Specification certificatesSortedByDate(boolean asc){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .orderBy(CertificateTable.creationDate);
        if(!asc){
            builder.desc();
        }
        logger.debug("SQL certificatesSortedByDate : " + builder.toSqlClauses());
        return builder;
    }

    public static Specification certificatesSortedByName(boolean asc){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .orderBy(CertificateTable.name);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByTagSortedByDate(String tag, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) certificatesByTag(tag);
        builder.orderBy(CertificateTable.creationDate);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByTagSortedByName(String tag, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) certificatesByTag(tag);
        builder.orderBy(CertificateTable.name);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByTag(String name){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select(CertificateTable.certificateId,
                CertificateTable.certificateName,
                CertificateTable.certificateDescription,
                CertificateTable.certificatePrice,
                CertificateTable.certificateCreationDate,
                CertificateTable.certificateModificationDate,
                CertificateTable.certificateDuration)
                .from(CertificateTable.tableName)
                .innerJoin(CertificateTagTable.tableName,
                        CertificateTable.certificateId,
                        CertificateTagTable.relationCertificateId)
                .innerJoin(TagTable.tableName,
                        CertificateTagTable.relationTagId,
                        TagTable.tagId)
                .where()
                .equal(TagTable.tagName, name);
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
