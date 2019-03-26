package com.epam.esm.service;

import com.epam.esm.repository.config.CertificateTable;
import com.epam.esm.repository.config.CertificateTagTable;
import com.epam.esm.repository.config.TagTable;
import com.epam.esm.repository.repository.specification.SpecificationBuilder;
import com.epam.esm.service.util.Order;

public class CertificateDatabaseSpecification {
    public static void selectFromCertificate(SpecificationBuilder builder){
        builder.select(CertificateTable.certificateId,
                CertificateTable.certificateName,
                CertificateTable.certificateDescription,
                CertificateTable.certificatePrice,
                CertificateTable.certificateCreationDate,
                CertificateTable.certificateModificationDate,
                CertificateTable.certificateDuration)
                .from(CertificateTable.tableName);
    }

    public static void innerJoinTags(SpecificationBuilder builder){
        builder.innerJoin(CertificateTagTable.tableName,
                CertificateTable.certificateId,
                CertificateTagTable.relationCertificateId)
                .innerJoin(TagTable.tableName,
                        CertificateTagTable.relationTagId,
                        TagTable.tagId);
    }

    public static void whereTag(SpecificationBuilder builder, String name){
        builder.equal(TagTable.tagName, name);
    }

    public static void orderByDate(SpecificationBuilder builder, Order order){
        builder.orderBy(CertificateTable.creationDate);
        if(order == Order.DESC){
            builder.desc();
        }
    }

    public static void orderByName(SpecificationBuilder builder, Order order){
        builder.orderBy(CertificateTable.name);
        if(order == Order.DESC){
            builder.desc();
        }
    }

    public static void findByNamePart(SpecificationBuilder builder, String regex){
        builder.like(CertificateTable.certificateName, regex);
    }

    public static void findByDescriptionPart(SpecificationBuilder builder, String regex){
        builder.like(CertificateTable.certificateDescription, regex);
    }
}
