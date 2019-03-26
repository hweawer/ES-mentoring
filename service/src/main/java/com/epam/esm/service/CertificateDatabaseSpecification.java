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
        return findByLike(CertificateTable.name, pattern);
    }

    public static Specification certificatesByNamePartSortedByDate(String pattern, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) findByLike(CertificateTable.name, pattern);
        builder.orderBy(CertificateTable.creationDate);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByTagByNamePartSortedByDate(String tag, String pattern, boolean asc){
        return findByTagByNamePart(tag, pattern, asc, CertificateTable.creationDate);
    }

    public static Specification certificatesByTagByNamePartSortedByName(String tag, String pattern, boolean asc){
        return findByTagByNamePart(tag, pattern, asc, CertificateTable.certificateName);
    }

    private static Specification findByTagByNamePart(String tag, String pattern, boolean asc, String certificateName) {
        SpecificationBuilder builder = (SpecificationBuilder) certificatesByTag(tag);
        builder.where()
                .like(CertificateTable.certificateName, pattern);
        builder.orderBy(certificateName);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByTagByDescriptionPartSortedByDate(String tag, String pattern, boolean asc){
        return findByTagByDescriptionPart(tag, pattern, asc, CertificateTable.creationDate);
    }

    public static Specification certificatesByTagByDescriptionPartSortedByName(String tag, String pattern, boolean asc){
        return findByTagByDescriptionPart(tag, pattern, asc, CertificateTable.certificateName);
    }

    private static Specification findByTagByDescriptionPart(String tag, String pattern, boolean asc, String certificateName) {
        SpecificationBuilder builder = (SpecificationBuilder) certificatesByTag(tag);
        builder.where()
                .like(CertificateTable.certificateDescription, pattern);
        builder.orderBy(certificateName);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByNamePartSortedByName(String pattern, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) findByLike(CertificateTable.name, pattern);
        builder.orderBy(CertificateTable.name);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByDescriptionPartSortedByName(String pattern, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) findByLike(CertificateTable.description, pattern);
        builder.orderBy(CertificateTable.name);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByDescriptionPartSortedByDate(String pattern, boolean asc){
        SpecificationBuilder builder = (SpecificationBuilder) findByLike(CertificateTable.description, pattern);
        builder.orderBy(CertificateTable.creationDate);
        if(!asc){
            builder.desc();
        }
        return builder;
    }

    public static Specification certificatesByDescriptionPart(String pattern){
        return findByLike(CertificateTable.description, pattern);
    }

    private static Specification findByLike(String column, String pattern) {
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.select()
                .from(CertificateTable.tableName)
                .where()
                .like(column, pattern);
        logger.debug("SQL findByLike : " + builder.toSqlClauses());
        return builder;
    }
}
