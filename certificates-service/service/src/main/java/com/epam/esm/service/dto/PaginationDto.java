package com.epam.esm.service.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PaginationDto<T> {
    private Collection<T> collection;
    private String first;
    private String last;
    private String next;
    private String previous;
}
