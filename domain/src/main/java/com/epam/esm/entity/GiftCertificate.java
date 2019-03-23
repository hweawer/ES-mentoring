package com.epam.esm.entity;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GiftCertificate implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    @Pattern(regexp = "\\p{L}{3,12}")
    private String name;

    @NotBlank
    @Size(min = 3)
    private String description;

    @NotNull
    @PositiveOrZero
    @Digits(integer=11, fraction=2)
    private BigDecimal price;

    @NotNull
    @Past
    private LocalDate creationDate;
    private LocalDate modificationDate;

    @NotNull
    @Min(value = 5)
    @Max(value = 365)
    private Short duration;
    private Set<Tag> tags = new HashSet<>();

    public GiftCertificate(){}

    public GiftCertificate(String name,
                           String description,
                           BigDecimal price,
                           LocalDate creationDate,
                           LocalDate modificationDate,
                           Short duration, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.duration = duration;
        this.tags = tags;
    }

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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                creationDate.equals(that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                duration.equals(that.duration) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, creationDate, modificationDate, duration, tags);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
