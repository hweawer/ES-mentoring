package com.epam.esm.repository.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {
    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        final String ROLE_BY_NAME = "select r from Role r where r.type=:name";
        return entityManager.createQuery(ROLE_BY_NAME, Role.class).setParameter("name", name).getResultStream().findFirst();
    }
}
