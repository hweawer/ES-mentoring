package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "certificates")
public class GiftCertificate implements Serializable {
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    @NonNull
    private String description;

    @Getter
    @Setter
    @NonNull
    private BigDecimal price;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @Column(name = "modification_date")
    private LocalDate modificationDate;

    @Getter
    @Setter
    @NonNull
    private Short duration;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "certificates_tags",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
