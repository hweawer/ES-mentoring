package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @NonNull
    private RoleType type;

}
