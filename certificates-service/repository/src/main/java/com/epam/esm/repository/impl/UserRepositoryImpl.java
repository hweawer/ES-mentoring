package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findUserByLogin(String login) {
        final String USER_BY_LOGIN = "select u from User as u where u.login=:login";
        return entityManager.createQuery(USER_BY_LOGIN, User.class).setParameter("login", login).getSingleResult();

    }
}
