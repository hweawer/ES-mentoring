package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificates_snapshots")
public class CertificateSnapshot implements Serializable {
    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NonNull
    private Short duration;

    public CertificateSnapshot(GiftCertificate certificate){
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.description = certificate.getDescription();
        this.price = certificate.getPrice();
        this.creationDate = certificate.getCreationDate();
        this.duration = certificate.getDuration();
    }
}