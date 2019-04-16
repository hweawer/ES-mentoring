package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Links {
    private String first;
    private String last;
    private String next;
    private String prev;
}
