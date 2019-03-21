package com.epam.esm.service.application;

import com.epam.esm.config.DbColumns;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specification.FromSpecification;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationBuilder;
import com.epam.esm.repository.specification.WhereSpecification;
import com.epam.esm.repository.specification.condition.EqualsSpecification;
import com.epam.esm.repository.specification.condition.LikeSpecification;
import com.epam.esm.repository.specification.join.InnerJoin;
import com.epam.esm.repository.specification.util.OrderBySpecification;

import java.util.Collection;

import static com.epam.esm.config.DbColumns.*;

public class DatabaseSpecificationCreator {
    public Collection<Specification> findAllGifts(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(certificateTable));
        return builder.getSpecifications();
    }

    public Collection<Specification> tagByName(String name){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(tagsTable));
        builder.addSpecification(
                new WhereSpecification(new EqualsSpecification(name, name)));
        return builder.getSpecifications();
    }

    public Collection<Specification> tagsByCertificate(GiftCertificate certificate){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(certificateTable));
        builder.addSpecification(new InnerJoin(relationTable, certificateId, relationCertificateTableId));
        builder.addSpecification(new InnerJoin(tagsTable, relationTagTableId, tagId));
        builder.addSpecification(
                new WhereSpecification(new EqualsSpecification(certificateId, certificate.getId())));
        return builder.getSpecifications();
    }

    public Collection<Specification> findById(Long id, Class entity){
        String tableName = entity == Tag.class ? tagsTable : certificateTable;
        return byId(id, tableName);
    }

    public Collection<Specification> certificatesSortedByDate(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(certificateTable));
        builder.addSpecification(new OrderBySpecification(creationDate));
        return builder.getSpecifications();
    }

    public Collection<Specification> certificatesSortedByName(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(certificateTable));
        builder.addSpecification(new OrderBySpecification(name));
        return builder.getSpecifications();
    }

    public Collection<Specification> certificatesByNamePart(String part){
        return byLike(part, name);
    }

    public Collection<Specification> certificatesByDescriptionPart(String part){
        return byLike(part, description);
    }

    public Collection<Specification> findAllTags(){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(tagsTable));
        return builder.getSpecifications();
    }

    private Collection<Specification> byLike(String part, String name) {
        String regex = "%" + part + "%";
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(certificateTable));
        builder.addSpecification(new WhereSpecification(new LikeSpecification(name, regex)));
        return builder.getSpecifications();
    }

    private Collection<Specification> byId(Long id, String tableName){
        SpecificationBuilder builder = new SpecificationBuilder();
        builder.addSpecification(new FromSpecification(tableName));
        builder.addSpecification(
                new WhereSpecification(new EqualsSpecification(DbColumns.id, id)));
        return builder.getSpecifications();
    }
}
