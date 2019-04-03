package com.epam.esm.generated;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GiftCertificate.class)
public abstract class GiftCertificate_ {

	public static volatile SingularAttribute<GiftCertificate, Short> duration;
	public static volatile SingularAttribute<GiftCertificate, LocalDate> modificationDate;
	public static volatile SingularAttribute<GiftCertificate, BigDecimal> price;
	public static volatile SingularAttribute<GiftCertificate, String> name;
	public static volatile SingularAttribute<GiftCertificate, String> description;
	public static volatile SingularAttribute<GiftCertificate, Long> id;
	public static volatile SingularAttribute<GiftCertificate, LocalDate> creationDate;
	public static volatile SetAttribute<GiftCertificate, Tag> tags;

	public static final String DURATION = "duration";
	public static final String MODIFICATION_DATE = "modificationDate";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CREATION_DATE = "creationDate";
	public static final String TAGS = "tags";

}

