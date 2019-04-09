package com.epam.esm.authentication.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUser extends User {

    public CustomUser(com.epam.esm.authentication.entity.User user) {
        super(user.getLogin(), user.getPassword(), user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getType().name().toUpperCase())).collect(Collectors.toList()));
    }
}
