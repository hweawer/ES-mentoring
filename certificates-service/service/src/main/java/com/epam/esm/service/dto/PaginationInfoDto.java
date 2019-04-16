package com.epam.esm.service.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PaginationInfoDto<T> {
    private Collection<T> collection;
    private PageInfo pageInfo;
}
