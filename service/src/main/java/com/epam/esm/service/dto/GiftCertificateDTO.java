package com.epam.esm.service.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class GiftCertificateDTO {
    private Long id;

    @NotNull @Pattern(regexp = "\\p{L}{3,12}")
    private String name;

    @NotBlank @Size(min = 3)
    private String description;

    @NotNull @PositiveOrZero @Digits(integer=12, fraction=2)
    private BigDecimal price;

    @Past
    private LocalDate creationDate;

    @PastOrPresent
    private LocalDate modificationDate;

    @NotNull @Min(value = 5) @Max(value = 365)
    private Short duration;

    @Valid
    private Set<TagDTO> tags;

    public GiftCertificateDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", duration=" + duration +
                '}';
    }
}
