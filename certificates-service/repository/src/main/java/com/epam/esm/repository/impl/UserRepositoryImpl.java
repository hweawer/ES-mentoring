package com.epam.esm.repository.impl;

import com.epam.esm.entity.RoleType;
import com.epam.esm.entity.User;
import com.epam.esm.generated.User_;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(User_.LOGIN), login));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }
}
