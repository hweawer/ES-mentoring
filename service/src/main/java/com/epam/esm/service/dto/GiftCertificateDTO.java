package com.epam.esm.service.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GiftCertificateDTO {
    private Long id;

    @NotNull @Pattern(regexp = "\\p{L}{3,12}")
    private String name;

    @NotBlank @Size(min = 3)
    private String description;

    @NotNull @PositiveOrZero @Digits(integer=12, fraction=2)
    private BigDecimal price;

    @NotNull @Past
    private LocalDate creationDate;
    private LocalDate modificationDate;

    @NotNull @Min(value = 5) @Max(value = 365)
    private Short duration;
}
