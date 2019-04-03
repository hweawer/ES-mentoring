package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tags")
public class Tag implements Serializable {
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
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    private Set<GiftCertificate> certificates;
}
