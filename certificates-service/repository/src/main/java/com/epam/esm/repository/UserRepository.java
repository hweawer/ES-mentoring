package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    User findUserByLogin(String login);
}
