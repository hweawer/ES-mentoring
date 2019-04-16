package com.epam.esm.service.certificate.impl;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchCertificateRequest {
    @Min(value = 1, message = "incorrect.pagination")
    @Max(value = 100, message = "incorrect.pagination")
    private Integer limit = 5;
    @Min(value = 1, message = "incorrect.pagination")
    private Integer page = 1;
    private List<String> tag = new ArrayList<>();

    @Pattern(regexp = "description|name", message = "unknown.filter.attribute")
    private String column;

    private String value;

    @Pattern(regexp = "creationDate|name|-name|-creationDate", message = "unknown.sort.attribute")
    private String sort;
}
