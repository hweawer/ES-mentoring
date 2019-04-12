package com.epam.esm.service.dto;

import com.epam.esm.service.validation.ValidationScopes;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.epam.esm.service.validation.ValidationScopes.*;

@Data
@NoArgsConstructor
public class CertificateDto {

    private Long id;

    @NotNull(groups = onCreate.class, message = "")
    @Pattern(regexp = "\\p{L}{3,12}", groups = {onCreate.class, onPatch.class}, message = "")
    private String name;

    @NotNull(groups = onCreate.class, message = "")
    @Size(min = 3, groups = {onCreate.class, onPatch.class}, message = "")
    private String description;

    @NotNull(groups = onCreate.class, message = "")
    @PositiveOrZero(groups = {onCreate.class, onPatch.class}, message = "")
    @Digits(integer=11, fraction=2, groups = {onCreate.class, onPatch.class}, message = "")
    private BigDecimal price;

    @Past(message = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @PastOrPresent(message = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    @NotNull(groups = onCreate.class, message = "")
    @Min(value = 5, groups = {onCreate.class, onPatch.class}, message = "")
    @Max(value = 365, groups = {onCreate.class, onPatch.class}, message = "")
    private Short duration;

    @Valid
    private Set<TagDto> tags = new HashSet<>();
}
