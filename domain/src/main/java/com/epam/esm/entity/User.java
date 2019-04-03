package com.epam.esm.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {

    private String login;
    private String password;
    private Role role;
    private BigDecimal money;

}
