package com.epam.esm.service.dto;

import com.epam.esm.service.validation.ValidationScopes;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static com.epam.esm.service.validation.ValidationScopes.*;

@Data
@NoArgsConstructor
public class CertificateDto {

    private Long id;

    @NotNull(groups = onCreate.class, message = "certificate.name.null")
    @Pattern(regexp = "\\p{L}{3,12}", groups = {onCreate.class, onPatch.class}, message = "certificate.name.not.suite.pattern")
    private String name;

    @NotBlank(groups = onCreate.class, message = "certificate.description.blank")
    @Size(min = 3, groups = {onCreate.class, onPatch.class}, message = "certificate.description.size")
    private String description;

    @NotNull(groups = onCreate.class, message = "certificate.price.null")
    @PositiveOrZero(groups = {onCreate.class, onPatch.class}, message = "certificate.price.positive.or.zero")
    @Digits(integer=11, fraction=2, groups = {onCreate.class, onPatch.class}, message = "certificate.price.digits")
    private BigDecimal price;

    @Past(message = "date.creation.past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @PastOrPresent(message = "date.modification.past.present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    @NotNull(groups = onCreate.class, message = "duration.null")
    @Min(value = 5, groups = {onCreate.class, onPatch.class}, message = "duration.min")
    @Max(value = 365, groups = {onCreate.class, onPatch.class}, message = "duration.max")
    private Short duration;

    @Valid
    private Set<TagDto> tags;
}
