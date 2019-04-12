package com.epam.esm.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TagDto {
    private Long id;
    @NotNull(message = "")
    @Pattern(regexp = "^[\\p{L}0-9]{3,12}", message = "")
    private String name;
}
