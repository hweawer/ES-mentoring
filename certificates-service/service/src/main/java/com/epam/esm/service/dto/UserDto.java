package com.epam.esm.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String login;

    private String password;

    private BigDecimal money;
}
