package com.epam.esm.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class CertificateDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private Short duration;
    private Set<TagDto> tags;

    public CertificateDto(Long id, String name, String description, BigDecimal price, LocalDate creationDate, LocalDate modificationDate, Short duration, Set<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.duration = duration;
        this.tags = tags;
    }

    public CertificateDto() {
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

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificateDto)) return false;
        CertificateDto that = (CertificateDto) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                Objects.equals(creationDate, that.creationDate) &&
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
        return "CertificateDto{" +
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
