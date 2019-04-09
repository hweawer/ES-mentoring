package com.epam.esm.generated;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, BigDecimal> money;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> login;

	public static final String PASSWORD = "password";
	public static final String MONEY = "money";
	public static final String ROLES = "roles";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

