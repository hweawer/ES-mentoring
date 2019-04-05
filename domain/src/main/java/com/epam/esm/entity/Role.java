package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

}
