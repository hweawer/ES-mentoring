package com.epam.esm.authentication.repository;

import com.epam.esm.authentication.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role> {
    Optional<Role> findByName(String name);
}
