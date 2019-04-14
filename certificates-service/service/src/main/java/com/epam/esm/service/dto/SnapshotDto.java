package com.epam.esm.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class SnapshotDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private LocalDate creationDate;

    private Short duration;

    @Valid
    private Set<TagDto> tags;
}
