package com.epam.esm.authentication.repository;

import com.epam.esm.authentication.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByUsername(String username);
}
