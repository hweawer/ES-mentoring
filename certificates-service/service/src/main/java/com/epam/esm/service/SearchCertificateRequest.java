package com.epam.esm.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchCertificateRequest {
    private int limit = 5;
    private int page = 1;
    private List<String> tag = new ArrayList<>();

    @Pattern(regexp = "description|name")
    private String column;

    private String value;

    @Pattern(regexp = "date|name")
    private String sort;
}
