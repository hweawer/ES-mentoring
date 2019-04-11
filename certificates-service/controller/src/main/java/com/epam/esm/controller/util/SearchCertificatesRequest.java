package com.epam.esm.controller.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//todo: can create validation
@Data
@NoArgsConstructor
public class SearchCertificatesRequest {
    private int limit = 5;
    private int page = 1;
    private List<String> tag = new ArrayList<>();
    private String column;
    private String value;
    private String sort;
}
