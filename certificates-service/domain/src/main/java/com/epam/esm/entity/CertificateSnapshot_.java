package com.epam.esm.entity;

import com.epam.esm.entity.CertificateSnapshot;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CertificateSnapshot.class)
public abstract class CertificateSnapshot_ {

	public static volatile SingularAttribute<CertificateSnapshot, Short> duration;
	public static volatile SingularAttribute<CertificateSnapshot, LocalDate> modificationDate;
	public static volatile SingularAttribute<CertificateSnapshot, BigDecimal> price;
	public static volatile SingularAttribute<CertificateSnapshot, String> name;
	public static volatile SingularAttribute<CertificateSnapshot, String> description;
	public static volatile SingularAttribute<CertificateSnapshot, Long> id;
	public static volatile SingularAttribute<CertificateSnapshot, LocalDate> creationDate;

	public static final String DURATION = "duration";
	public static final String MODIFICATION_DATE = "modificationDate";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CREATION_DATE = "creationDate";

}

