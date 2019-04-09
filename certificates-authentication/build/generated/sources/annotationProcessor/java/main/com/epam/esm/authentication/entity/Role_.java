package com.epam.esm.authentication.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, RoleType> type;

	public static final String ID = "id";
	public static final String TYPE = "type";

}

