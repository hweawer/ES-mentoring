package com.epam.esm.service.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GiftCertificateDTO {
    private Long id;

    @NotNull(message = "certificate.name.null")
    @Pattern(regexp = "\\p{L}{3,12}", message = "certificate.name.not.suite.pattern")
    private String name;

    @NotBlank(message = "certificate.description.blank")
    @Size(min = 3, message = "certificate.description.size")
    private String description;

    @NotNull(message = "certificate.price.null")
    @PositiveOrZero(message = "certificate.price.positive.or.zero")
    @Digits(integer=11, fraction=2, message = "")
    private BigDecimal price;

    @Past(message = "date.creation.past")
    private LocalDate creationDate;

    @PastOrPresent(message = "date.modification.past.present")
    private LocalDate modificationDate;

    @NotNull(message = "duration.null")
    @Min(value = 5, message = "duration.min")
    @Max(value = 365, message = "duration.max")
    private Short duration;

    @Valid
    private Set<TagDTO> tags = new HashSet<>();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDTO)) return false;
        GiftCertificateDTO that = (GiftCertificateDTO) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                creationDate.equals(that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                duration.equals(that.duration) &&
                tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, creationDate, modificationDate, duration, tags);
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
