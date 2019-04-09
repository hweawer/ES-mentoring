package com.epam.esm.repository.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.Role_;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {
    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(Role_.NAME), name));
        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }
}
