package com.epam.esm.entity;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.RoleType;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, RoleType> name;
	public static volatile SingularAttribute<Role, Long> id;

	public static final String NAME = "name";
	public static final String ID = "id";

}

