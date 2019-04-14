package com.epam.esm.service.find;

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

    @Pattern(regexp = "description|name", message = "unknown.filter.attribute")
    private String column;

    private String value;

    @Pattern(regexp = "date|name", message = "unknown.sort.attribute")
    private String sort;
}
