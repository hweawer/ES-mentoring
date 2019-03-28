package com.epam.esm.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TagDTO {
    private Long id;

    @NotNull(message = "tag.name.null.value")
    @Pattern(regexp = "^[\\p{L}0-9]{3,12}", message = "tag.name.not.suite.pattern")
    private String name;

    public TagDTO() {}

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

    @Override
    public String toString() {
        return "TagDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
