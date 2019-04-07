package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @Column(name = "time")
    private Timestamp timestamp;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<CertificateSnapshot> certificates;
}
